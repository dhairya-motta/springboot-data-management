import markdown

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
    </style>
</head>
<body>
{markdown.markdown(md_text, extensions=['fenced_code'])}
</body>
</html>
"""

with open("Report.html", "w", encoding="utf-8") as file:
    file.write(html_content)

print("Report.html generated successfully. You can open it in your browser and use Ctrl+P to save it as a PDF.")
