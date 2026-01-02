# CAR MANAGEMENT & FUEL TRACKING

## Table of Contents

- [Description](#description)
- [Table of Contents](#table-of-contents)
- [Installation](#installation)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
- [Part 1](#Part1)
  - [Endpoints 1](#endpoints-1)
- [Part 2](#Part2)
  - [Endpoints 2](#endpoints-2)
- [Part 3](#Part3)
  - [CLI Commands](#CLI-Commands)
- [Authors](#authors)

## Description

Car Management & Fuel Tracking is a Java-based system consisting of a Spring Boot REST API and a standalone Java CLI client. It allows users to register cars, record fuel usage, and view fuel consumption statistics using in-memory storage. The project also includes a manually implemented Java Servlet to demonstrate HTTP request lifecycle handling.

## Installation

### Prerequisites

- SpringBoot FrameWork. You can download the latest version [here](https://spring.io/projects/spring-boot)
- Apache Maven. You can download the latest version [here](https://maven.apache.org/download.cgi)

### Setup

- To get started, clone the repository to your local machine and navigate into the director.

```bash
git clone https://github.com/kezagiselle/Codehills_Assignment.git
cd Codehills_Assignment
```

## Part 1

- Backend Implementation

### EndPoints

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/cars` | Create Car |
| GET | `/api/cars` | List Cars |
| POST | `/api/cars/{id}/fuel` | Add Fuel for a specific car|
| GET | `/api/cars/{id}/fuel/stats` | Get Stats for a specific car |


## Part 2

- Servlet Integration 

### EndPoints

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/servlet/fuel-stats?carId={id}` | Retrieves fuel statics for a specific car |


## Part 3

- Java CLI Application
- How to navigate to this project:

```bash
cd CLI-Application
```
- Run your backend server
- Then run this:
```bash
mvn spring-boot:run
```

### CLI Commands

```bash
- Create Car: create-car --brand <brand> --model <model> --year <year>
- Add Fuel: `add-fuel --carId <id> --liters <liters> --price <price> --odometer <odometer>`
- Fuel Stats: `fuel-stats --carId <id>`
- List Cars:  `list-cars`
- Car Info: `car-info --carId <id>`
- Help: `help`
- Exit: `exit`
```

## Authors

- [Gisele Keza](https://www.linkedin.com/in/keza-gisele-developer/)

