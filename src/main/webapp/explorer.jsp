<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.File" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <title>Java explorer app</title>
</head>
<body>
    <c:set var="title" value="Проводник" />
    <div class="container shadow-lg p-5 mt-5 mb-5 bg-white rounded">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="mb-4">${title}</h1>
            <div>
                <span class="me-3">Ваш логин: ${username}</span>
                <a href="logout" class="btn btn-outline-danger">
                    <i class="bi bi-box-arrow-right"></i> Выйти
                </a>
            </div>
        </div>
        <div class="content">
            <p><i class="bi bi-clock-history"></i> Время: ${currentTime}</p>
            <p><i class="bi bi-folder"></i> Текущий путь: ${currentPath.replace("\\", " > ")}</p>
            <p>
                <a class="btn btn-outline-success" href="files?path=${URLEncoder.encode(parentPath, 'UTF-8')}">
                    <i class="bi bi-arrow-90deg-up"></i> Вверх
                </a>
            </p>
            <ul class="list-unstyled">
                <c:forEach var="file" items="${files}">
                    <li class="mb-2">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <c:if test="${file.directory}">
                                            <i class="bi bi-folder2-open"></i>
                                            <a class="text-decoration-none" href="files?path=${URLEncoder.encode(file.absolutePath, 'UTF-8')}">
                                                ${file.name}/
                                            </a>
                                        </c:if>
                                        <c:if test="${!file.directory}">
                                            <i class="bi bi-file-earmark-text"></i>
                                            <a class="text-decoration-none" href="download?fileDownloadPath=${URLEncoder.encode(file.absolutePath, 'UTF-8')}">
                                                ${file.name}
                                            </a>
                                        </c:if>
                                    </div>
                                    <div>
                                        <c:if test="${!file.directory}">
                                            <span class="badge bg-light">
                                                <%
                                                    File file = (File) pageContext.getAttribute("file");
                                                    long size = file.length();
                                                    if (size < 1024) out.print(size + " B");
                                                    else if (size < 1024 * 1024) out.print(String.format("%.1f KB", size / 1024.0));
                                                    else if (size < 1024 * 1024 * 1024) out.print(String.format("%.1f MB", size / (1024.0 * 1024)));
                                                    else out.print(String.format("%.1f GB", size / (1024.0 * 1024 * 1024)));
                                                %>
                                            </span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="mt-2 text-muted small">
                                    <i class="bi bi-calendar3"></i>
                                    <%
                                        File file = (File) pageContext.getAttribute("file");
                                        out.print(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(file.lastModified())));
                                    %>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</body>
</html>