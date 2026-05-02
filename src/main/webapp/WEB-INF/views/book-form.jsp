<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>${book.id == null ? 'Add Book' : 'Update Book'}</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f7f6;
                margin: 0;
                padding: 20px;
            }

            .container {
                max-width: 500px;
                margin: auto;
                background: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            h2 {
                color: #333;
                text-align: center;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                color: #666;
            }

            input[type="text"],
            input[type="number"],
            select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            .btn-submit {
                width: 100%;
                padding: 10px;
                background-color: #28a745;
                border: none;
                color: white;
                font-size: 16px;
                border-radius: 4px;
                cursor: pointer;
            }

            .btn-submit:hover {
                background-color: #218838;
            }

            .alert-error {
                color: #721c24;
                background-color: #f8d7da;
                border-color: #f5c6cb;
                padding: 10px;
                border-radius: 4px;
                margin-bottom: 15px;
            }

            .back-link {
                display: block;
                text-align: center;
                margin-top: 15px;
                text-decoration: none;
                color: #007BFF;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h2>${book.id == null ? 'Add New Book' : 'Update Book Details'}</h2>

            <c:if test="${not empty errorMessage}">
                <div class="alert-error">${errorMessage}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/books/${book.id == null ? 'add' : 'edit/'.concat(book.id)}"
                method="post">
                <div class="form-group">
                    <label for="title">Book Title</label>
                    <input type="text" id="title" name="title" value="${book.title}" required />
                </div>

                <div class="form-group">
                    <label for="publicationYear">Publication Year</label>
                    <input type="number" id="publicationYear" name="publicationYear" value="${book.publicationYear}"
                        required />
                </div>

                <div class="form-group">
                    <label for="author">Author</label>
                    <select id="author" name="author.id" required>
                        <option value="" disabled ${book.author==null ? 'selected' : '' }>-- Select Author --</option>
                        <c:forEach var="author" items="${authors}">
                            <option value="${author.id}" ${book.author !=null && book.author.id==author.id ? 'selected'
                                : '' }>
                                ${author.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn-submit">${book.id == null ? 'Save Book' : 'Update Book'}</button>
            </form>

            <a href="${pageContext.request.contextPath}/books" class="back-link">Back to Book List</a>
        </div>
    </body>

    </html>