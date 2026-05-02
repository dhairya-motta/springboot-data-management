from playwright.sync_api import sync_playwright
import time
import requests
import os
import markdown

def wait_for_server():
    print("Waiting for server to start...", flush=True)
    for _ in range(120):
        try:
            r = requests.get('http://localhost:8080/books')
            if r.status_code == 200:
                print("Server is up!", flush=True)
                return True
        except:
            time.sleep(2)
    return False

if wait_for_server():
    with sync_playwright() as p:
        browser = p.chromium.launch()
        context = browser.new_context(viewport={"width": 1280, "height": 800})
        page = context.new_page()
        
        # Add visual delay for rendering
        def snap(url, path):
            page.goto(url)
            page.wait_for_load_state('networkidle')
            time.sleep(1) # Extra buffer for CSS processing just in case
            page.screenshot(path=path)
            print(f"Took screenshot of {path}")

        snap('http://localhost:8080/books', 'screenshot_list.png')
        snap('http://localhost:8080/books/add', 'screenshot_add.png')
        snap('http://localhost:8080/books/edit/1', 'screenshot_edit.png')

        browser.close()
        print("Screenshot generation completed.")

print("Generating HTML...", flush=True)
# Generate HTML
with open("Report.md", "r", encoding="utf-8") as file:
    md_text = file.read()

html_content = f"""
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Report</title>
    <style>
        body {{ font-family: Arial, sans-serif; line-height: 1.6; margin: 40px; }}
        h1, h2, h3 {{ color: #333; }}
        code {{ background: #f4f4f4; padding: 2px 4px; border-radius: 4px; }}
        pre {{ background: #f4f4f4; padding: 10px; border-radius: 4px; overflow-x: auto; }}
        a {{ color: #0066cc; }}
        img {{ max-width: 100%; border: 1px solid #ccc; border-radius: 4px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 15px 0; }}
    </style>
</head>
<body>
{markdown.markdown(md_text, extensions=['fenced_code'])}
</body>
</html>
"""

with open("Report.html", "w", encoding="utf-8") as file:
    file.write(html_content)

print("HTML generated successfully.", flush=True)

print("Generating PDF from HTML...", flush=True)
with sync_playwright() as p:
    browser = p.chromium.launch()
    page = browser.new_page()
    html_path = "file:///" + os.path.abspath("Report.html").replace('\\', '/')
    page.goto(html_path)
    # Ensure images load
    page.wait_for_load_state('networkidle')
    page.pdf(path="Report.pdf", format="A4", margin={"top": "20px", "bottom": "20px", "left": "20px", "right": "20px"})
    browser.close()

print("Report.pdf generated successfully.")
