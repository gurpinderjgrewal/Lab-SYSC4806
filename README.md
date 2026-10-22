# SYSC 4806 - Lab 6: Client-Side

## Student Information
- **Name:** Jordan Grewal
- **Student Number:** 101287828
- **Course:** SYSC 4806 – Software Engineering Lab
- **Repository:** https://github.com/gurpinderjgrewal/Lab-SYSC4806

---

## Deployment
- **Azure Web App URL:** https://addressbook-fwbdfqbtardtbqes.eastus-01.azurewebsites.net

---

## Project Overview
This project implements a Spring Boot RESTful address book application with persistent entities using Spring Data JPA, an in-memory H2 database, Thymeleaf UI templates, and continuous deployment through Microsoft Azure.

---

## Test Suite
Integration and unit tests are built using **JUnit 5**, **Spring MockMvc**, and **Mockito**:

| Test Class | Scope / Description |
| :--- | :--- |
| `AddressBookControllerTest` | Verifies REST API endpoints (`GET`, `POST`), HTTP response codes (`200`, `201`, `404`), and JSON payload structures. |
| `AddressBookWebControllerTest` | Verifies view name resolution (`addressbook`, `error`) and model attributes passed to Thymeleaf. |
| `BuddyInfoTest` | Unit tests for `BuddyInfo` getters/setters, including the new `address` field. |

### Running Tests Locally
```bash
mvn clean test