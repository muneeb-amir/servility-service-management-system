-- Create the database
-- drop database ServiceManagementSystem;

CREATE DATABASE ServiceManagementSystem;
-- Use the created database
USE ServiceManagementSystem;

-- Create the ServiceSeekers table (details for service seekers)
CREATE TABLE ServiceSeekers (
    seeker_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DOUBLE DEFAULT 0.0
);

-- Create the ServiceProviders table (details for service providers)
CREATE TABLE ServiceProviders 
(
    provider_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    expertise VARCHAR(255) NOT NULL,
    balance DOUBLE DEFAULT 0.0
);

-- Create the Services table (services offered by providers)
CREATE TABLE Services 
(
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    provider_id INT NOT NULL, -- References the ServiceProviders table
    description TEXT NOT NULL,
    expertise varchar(50),
    price DOUBLE NOT NULL,
    sname varchar(50),
	status ENUM('pending', 'accepted', 'completed') DEFAULT 'pending',
    FOREIGN KEY (provider_id) REFERENCES ServiceProviders(provider_id) ON DELETE CASCADE
);
update Services set status ='pending' where service_id;
 
ALTER TABLE Services
MODIFY COLUMN status VARCHAR(50) DEFAULT 'pending';
-- Create the ServiceRequests table (requests made by seekers)
CREATE TABLE ServiceRequests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    seeker_id INT NOT NULL, -- References the ServiceSeekers table
    expertise VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    budget DOUBLE NOT NULL,
    providername varchar(50) NULL,
     status ENUM('pending', 'accepted', 'completed') DEFAULT 'pending',

    FOREIGN KEY (seeker_id) REFERENCES ServiceSeekers(seeker_id) ON DELETE CASCADE
);





-- Query tables
SELECT * FROM ServiceSeekers;
SELECT * FROM ServiceProviders;
SELECT * FROM Services;-- provider posts request 
SELECT * FROM ServiceRequests;-- seeker posts request
update ServiceSeekers set balance=1000 where service_id='20';