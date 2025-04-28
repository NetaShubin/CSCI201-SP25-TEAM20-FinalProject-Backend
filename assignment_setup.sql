CREATE SCHEMA `201project` ;

CREATE TABLE `201project`.`user_assignment` (
  `username` VARCHAR(45) NOT NULL,
  `assignmentName` VARCHAR(45) NOT NULL,
  `status` INT NOT NULL); 
  
CREATE TABLE `201project`.`assignment` (
  `name` VARCHAR(45) NOT NULL,
  `courseID` INT NOT NULL,
  `dueDate` VARCHAR(45) NOT NULL,
  `dueTime` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `name_UNIQUE` (`name`));

Select * from `201project`.`assignment`;
Select * from `201project`.`user_assignment`;

-- SELECT assignment.name AS name, assignment.courseID AS courseID, 
-- assignment.dueDate AS dueDate,
-- assignment.dueTime AS dueTime, assignment.description AS description
-- FROM assignment
-- JOIN user_assignment ON user_assignment.assignmentName = assignment.name
-- WHERE user_assignment.username = 'bob';
