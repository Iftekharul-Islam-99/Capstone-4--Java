---
Poised - Project Management System 3.0
---
A project management system for structural engineers.

## Programme functionality

Reads data from a SQL database on existing projects and allows the user to edit/update the project details.
The user is also able to manually add a new project to the database. The project contains contact information of the
individuals involved and details on the project itself.

### Old features

User is able to:
* View information on the existing projects in the text files.
* Add new projects to these files.
* Update and finalise projects.
* View a list of uncompleted or overdue projects.
* Search for projects using the ID or the name.

User is able of add contact details of 3 individuals:
* Contractor.
* Customer.
* Architect.

### New features

* Data is now read and written to a SQL database callsed PoisePMS.
* Finalising a project now generates an invoice if there is still unpaid fees.
* Finalising a project now marks the project as finalised and a completion date is added.

## Guide

At startup all relevant information will be read from the PoisePMS database.
The user will then be presented with a menu.
The following options will be displayed:
* Add a new project.
* Update a projects detail.
* View all uncompleted projects.
* View all overdue projects.
* Search for a project.
* Quit.

**Add project**:
user is prompted to enter contact details of the contractor, architect and the customer.
The following fields are required to be completed for each individual:
* Name.
* Telephone number.
* Email.
* Address.

User is then required to enter the details of the project.
These fields must be completed:
* Name.
* ID.
* Address.
* ERF No.
* Building type.
* Fee.
* Deposit.
* Deadline.

When completed the details will be added to the database and can be accessed.

**Update Project**:
User is presented with a list of projects from the database.
User can enter the allocated number corresponding the project to select it or enter '-1' to exit back to the main menu.
When a project is chosen a new menu is generated with the following options:
* Update due date.
* Update contractor’s details.
* Update deposit amount.
* Finalise the project.
* Exit.

**FInalising**:
When the project is finalised the deposit amount and the fee will be compared and if there is still unpaid fees an invoice will be generated.
The invoice will contain the customer's details and the amount left to pay.
If a project is finalised then it will be marked as finalised and a completion date will be added to the project.

**Search**:
User can either enter the full project name or the full project ID.
If a matching project is selected, then the **update project** menu is generated.

__For each menu a character, character combination or numbers are assigned for the corresponding option. Users are just required to enter the correct character,
character combination or number to select the option.__

## Use

This can be used by companies that work with large volumes of projects or a large volume of projects simultaneously and
wish to store and have easy access to all the information on each project. This is also a convenient way of data storage as
it can easily be updated and modified, and new methods can easily be added to modify/interpret the data at any time.

## Instruction

Download the all the files and open all the *__.java__* files in the *__bin__* folder in an ide of choice and run the code.

## Contributors

Made by *Iftekharul islam*

  

