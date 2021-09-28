<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Employees</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row">
        <h3 class="text-center">List of employees</h3>
    </div>
    <div class="row">
        <div class="container">
            <div class="row">
                <div class="col-lg-1">
                </div>
                <div class="col-lg-10">
                    <div class="mb-3">
                        <table class="table table-hover table-striped">
                            <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">First name</th>
                                <th scope="col">Last name</th>
                                <th scope="col">Job title</th>
                                <th scope="col">Gander</th>
                                <th scope="col">Date of birth</th>
                                <th scope="col">Department</th>
                                <th scope="col">Actions</th>
                            </tr>
                            </thead>
                            <tbody id="employee">

                            </tbody>
                        </table>
                    </div>
                    <div class="mb-3">
                        <button type="button" class="btn btn btn-outline-success" id="newEmployee">New employee
                        </button>
                        <button type="button" class="btn btn btn-outline-secondary" id="toggleEmployeeInfo"
                                data-bs-toggle="collapse" data-bs-target="#employeeInfo">Show/hide employee information
                        </button>
                    </div>
                    <div id="employeeInfo" class="collapse">
                        <div class="card border-success mb-3">
                            <div class="card-header">
                                Employee information
                            </div>
                            <div class="card-body">
                                <form id="employeeForm">
                                    <input type="hidden" class="form-control" id="id">
                                    <div class="mb-3 row">
                                        <div class="col">
                                            <label for="firstName" class="form-label">First name</label>
                                            <input type="text" class="form-control" id="firstName">
                                        </div>
                                        <div class="col">
                                            <label for="lastName" class="form-label">Last name</label>
                                            <input type="text" class="form-control" id="lastName">
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <div class="col">
                                            <label for="dateOfBirth" class="form-label">Date of birth</label>
                                            <input type="date" class="form-control" id="dateOfBirth">
                                        </div>
                                        <div class="col">
                                            <label class="form-label">Gender</label>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" id="male"
                                                       name="flexRadioDefault">
                                                <label class="form-check-label" for="male">
                                                    Male
                                                </label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" id="female"
                                                       name="flexRadioDefault"checked>
                                                <label class="form-check-label" for="female">
                                                    Female
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <div class="col">
                                            <label for="jobTitle" class="form-label">Job title</label>
                                            <input type="text" class="form-control" id="jobTitle">
                                        </div>
                                        <div class="col">
                                            <label for="department" class="form-label">Department</label>
                                            <select id="department" class="form-select" aria-label="Select department">
                                                <option value="0" selected>Open this select menu</option>
                                                <option value="1">Management department</option>
                                                <option value="2">Software department</option>
                                                <option value="3">Support department</option>
                                            </select>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-success">Submit</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-1">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/resources/js/employeeHandler.js"></script>
</body>
</html>
