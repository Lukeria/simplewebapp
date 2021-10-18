create table if not exists employee
(
    employee_id   bigserial primary key,
    first_name    varchar(100) not null,
    last_name     varchar(100),
    department_id integer,
    job_title     varchar(150),
    gender        varchar(100) not null,
    date_of_birth date         not null
);