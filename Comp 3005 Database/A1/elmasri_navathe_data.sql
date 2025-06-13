BEGIN TRANSACTION;

DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS DEPARTMENT;
DROP TABLE IF EXISTS DEPT_LOCATIONS;
DROP TABLE IF EXISTS PROJECT;
DROP TABLE IF EXISTS WORKS_ON;
DROP TABLE IF EXISTS DEPENDENT;

CREATE TABLE EMPLOYEE (
    fname TEXT,
    minit TEXT,
    lname TEXT,
    ssn TEXT NOT NULL PRIMARY KEY,
    bdate TEXT,
    address TEXT,
    sex TEXT,
    salary REAL,
    superssn TEXT,
    dno INTEGER,
    FOREIGN KEY (superssn) REFERENCES EMPLOYEE(ssn),
    FOREIGN KEY (dno) REFERENCES DEPARTMENT(dnumber)
);

CREATE TABLE DEPARTMENT (
    dname TEXT,
    dnumber INTEGER NOT NULL PRIMARY KEY,
    mgrssn TEXT,
    mgrstartdate TEXT,
    FOREIGN KEY (mgrssn) REFERENCES EMPLOYEE(ssn)
);

CREATE TABLE DEPT_LOCATIONS (
    dnumber INTEGER NOT NULL,
    dlocation TEXT NOT NULL,
    PRIMARY KEY (dnumber, dlocation),
    FOREIGN KEY (dnumber) REFERENCES DEPARTMENT(dnumber)
);

CREATE TABLE PROJECT (
    pname TEXT,
    pnumber INTEGER NOT NULL PRIMARY KEY,
    plocation TEXT,
    dnum INTEGER,
    FOREIGN KEY (dnum) REFERENCES DEPARTMENT(dnumber)
);

CREATE TABLE WORKS_ON (
    essn TEXT NOT NULL,
    pno INTEGER NOT NULL,
    hours REAL,
    PRIMARY KEY (essn, pno),
    FOREIGN KEY (essn) REFERENCES EMPLOYEE(ssn),
    FOREIGN KEY (pno) REFERENCES PROJECT(pnumber)
);

CREATE TABLE DEPENDENT (
    essn TEXT NOT NULL,
    dependent_name TEXT NOT NULL,
    sex TEXT,
    bdate TEXT,
    relationship TEXT,
    PRIMARY KEY (essn, dependent_name),
    FOREIGN KEY (essn) REFERENCES EMPLOYEE(ssn)
);

INSERT INTO EMPLOYEE (fname, minit, lname, ssn, bdate, address, sex, salary, superssn, dno)
VALUES 
('John', 'B', 'Smith', '123456789', '1955-01-09', '731 Fondren', 'M', 30000, '333445555', 5),
('Franklin', 'T', 'Wong', '333445555', '1945-12-08', '638 Voss', 'M', 40000, '888665555', 5),
('Alicia', 'J', 'Zelaya', '999887777', '1958-07-19', '3321 Castle', 'F', 25000, '987987987', 4),
('Jennifer', 'S', 'Wallace', '987654321', '1931-06-20', '291 Berry', 'F', 43000, '888665555', 4),
('Ramesh', 'K', 'Narayan', '666884444', '1952-09-15', '975 Fire Oak', 'M', 38000, '333445555', 5),
('Joyce', 'A', 'English', '453453453', '1962-07-31', '5631 Rice', 'F', 25000, '333445555', 5),
('Ahmad', 'V', 'Jabber', '987987987', '1959-03-29', '980 Dallas', 'M', 25000, '987654321', 4),
('James', 'E', 'Borg', '888665555', '1927-11-10', '450 Stone', 'M', 55000, NULL, 1);

INSERT INTO DEPARTMENT (dname, dnumber, mgrssn, mgrstartdate)
VALUES
('Research', 5, '123456789', '1978-05-22'),
('Administration', 3, '987654321', '1985-01-01'),
('Headquarters', 1, '888665555', '1971-06-19');

INSERT INTO DEPT_LOCATIONS (dnumber, dlocation)
VALUES
(1, 'Houston'),
(4, 'Stafford'),
(5, 'Bellaire'),
(5, 'Sugarland'),
(5, 'Dallas');

INSERT INTO PROJECT (pname, pnumber, plocation, dnum)
VALUES
('ProductX', 1, 'Bellaire', 5),
('ProductY', 2, 'Sugarland', 5),
('ProductZ', 3, 'Houston', 5),
('Computerization', 10, 'Staffort', 4),
('Reorganization', 20, 'Houston', 1),
('NewBenefits', 30, 'Stafford', 4);

INSERT INTO WORKS_ON (essn, pno, hours)
VALUES
('123456789', 1, 32.50),
('123456789', 2, 7.50),
('666884444', 3, 40.00),
('453453453', 1, 20.00),
('453453453', 2, 20.00),
('333445555', 3, 10.00),
('333445555', 10, 10.00),
('333445555', 20, 10.00),
('999887777', 10, 10.00),
('987987987', 10, 35.00),
('987987987', 30, 5.00),
('987654321', 30, 20.00),
('987654321', 20, 15.00),
('888665555', 20, NULL);

INSERT INTO DEPENDENT (essn, dependent_name, sex, bdate, relationship)
VALUES
('333445555', 'Alice', 'F', '1976-04-05', 'DAUGHTER'),
('333445555', 'Theodore', 'M', '1973-10-25', 'SON'),
('333445555', 'Joy', 'F', '1948-05-03', 'SPOUSE'),
('987654321', 'Abner', 'M', '1932-02-29', 'SPOUSE'),
('123456789', 'Michael', 'M', '1978-01-01', 'SON'),
('123456789', 'Alice', 'F', '1978-01-31', 'DAUGHTER'),
('123456789', 'Elizabeth', 'F', '1957-05-05', 'SPOUSE');

COMMIT;