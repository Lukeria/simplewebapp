$(document).ready(function () {

    getEmployeeList();

    $('#newEmployee').click(function () {
        let employeeInfo = $('#employeeInfo');
        employeeInfo.removeClass("show");
        clearForm();
        employeeInfo.addClass("show");
    });

    $("#employeeForm").submit(function (event) {
        event.preventDefault();
        let id = $(this).find('#id').val();
        if (id === "" || id === undefined) {
            $('#employeeInfo').removeClass("show");
            saveEmployee();
        } else {
            $('#employeeInfo').removeClass("show");
            updateEmployee(id);
        }
        clearForm();
    });

});

function getEmployeeList() {
    $.ajax({
        type: "GET",
        url: "/employees/",
        success: function (response) {

            if (Array.isArray(response)) {
                response.forEach(element => {
                    addTableRow(element, "#employee");
                })
            }

        },
        error: function (e) {
            alert('Error: ' + e + '. Cannot load data');
        }
    });
}

function getEmployee(id) {

    $.ajax({
        type: "GET",
        url: "/employees/" + id,
        success: function (response) {

            let employeeInfo = $('#employeeInfo');
            employeeInfo.removeClass("show");
            fillTheForm(response);
            employeeInfo.addClass("show");
        },
        error: function (e) {
            alert('Error: ' + e + '. Cannot load data');
        }
    });
}

function deleteEmployee(id, currentRow) {

    $.ajax({
        type: "DELETE",
        url: "/employees/" + id,
        success: function () {
            currentRow.remove();
        },
        error: function (e) {
            alert('Error: ' + e + '. Cannot delete data');
        }
    });
}

function updateEmployee(id) {

    let employee = createEmployeeObject();
    $.ajax({
        type: "PUT",
        url: "/employees/" + id,
        contentType: "application/json",
        data: JSON.stringify(employee),
        success: function (response) {
            let currentRow = $('#employee #id:contains(' + response.employeeId + ')').parents('tr');
            updateTableRow(employee, currentRow);
        },
        error: function (e) {
            alert('Error: ' + e + '. Cannot update data');
        }
    });
}

function saveEmployee() {

    let employee = createEmployeeObject();
    $.ajax({
        type: "POST",
        url: "/employees/",
        contentType: "application/json",
        data: JSON.stringify(employee),
        success: function (response) {
            addTableRow(response, "#employee");
        },
        error: function (e) {
            alert('Error: ' + e + '. Cannot save data');
        }
    });
}

///////////////


function addTableRow(object, tableName) {

    let currentRow = $(tableName + '>tr:last-child');
    let currentId = 0;
    if (currentRow.length !== 0) {
        currentId = Number.parseInt(currentRow[0].id.split("_")[1]) || 0;
    }
    let rowNumber = currentId + 1;

    let tableRow = $('<tr id="row_' + rowNumber + '"></tr>');

    updateTableRow(object, tableRow)

    $(tableName).append(tableRow);
}

function updateTableRow(object, currentRow) {

    currentRow.empty();
    currentRow.append('<td id="id">' + object.employeeId + '</td>');
    currentRow.append('<td id="firstName">' + object.firstName + '</td>');
    currentRow.append('<td id="lastName">' + object.lastName + '</td>');
    currentRow.append('<td id="jobTitle">' + object.jobTitle + '</td>');
    currentRow.append('<td id="gender">' + object.gender + '</td>');

    let options = {year: 'numeric', month: 'long', day: 'numeric'};
    let date = new Date(object.dateOfBirth);
    currentRow.append('<td id="dateOfBirth">' + date.toLocaleDateString("en-US", options) + '</td>');

    currentRow.append('<td id="department">' + ((object.department !== 0) ? object.department : '') + '</td>');

    currentRow.append('<td id="actions">' +
        '<button type="button" class="btn btn-outline-info btn-sm" id="update">Edit</button>\n' +
        '<button type="button" class="btn btn-outline-danger btn-sm" id="delete">Delete</button>' +
        '</td>');

    currentRow.find('#update').click(function () {
        let id = $(this).parents('tr').find('#id').text();
        getEmployee(id);
    });

    currentRow.find('#delete').click(function () {
        let currentRow = $(this).parents('tr');
        let id = currentRow.find('#id').text();
        deleteEmployee(id, currentRow);
        clearForm();
    })
}


function fillTheForm(object) {

    let currentForm = $('#employeeForm');

    currentForm.find('#id').val(object.employeeId);
    currentForm.find('#firstName').val(object.firstName);
    currentForm.find('#lastName').val(object.lastName);
    currentForm.find('#jobTitle').val(object.jobTitle);

    object.dateOfBirth[1] = ('0' + object.dateOfBirth[1]).slice(-2);
    object.dateOfBirth[2] = ('0' + object.dateOfBirth[2]).slice(-2);
    currentForm.find('#dateOfBirth').val(object.dateOfBirth.join('-'));

    if (object.gender === "FEMALE") {
        currentForm.find('#female').prop('checked', true);
    } else if (object.gender === "MALE") {
        currentForm.find('#male').prop('checked', true);
    }

    currentForm.find('#department').val(object.department);
}

function clearForm() {

    let currentForm = $('#employeeForm');
    currentForm.find('#id').val('');
    currentForm.find('#firstName').val('');
    currentForm.find('#lastName').val('');
    currentForm.find('#jobTitle').val('');
    currentForm.find('#dateOfBirth').val('');
    currentForm.find('#female').prop('checked', true);
    currentForm.find('#department').val(0);
}

function createEmployeeObject() {

    let currentForm = $('#employeeForm');

    let employeeObject = {};
    employeeObject['employeeId'] = currentForm.find('#id').val();
    employeeObject['firstName'] = currentForm.find('#firstName').val();
    employeeObject['lastName'] = currentForm.find('#lastName').val();
    employeeObject['jobTitle'] = currentForm.find('#jobTitle').val();
    employeeObject['dateOfBirth'] = currentForm.find('#dateOfBirth').val();
    employeeObject['department'] = currentForm.find('#department>option:selected').val();
    if (currentForm.find('#female').is(":checked")) {
        employeeObject['gender'] = "FEMALE";
    } else if (currentForm.find('#male').is(":checked")) {
        employeeObject['gender'] = "MALE";
    }

    return employeeObject;

}