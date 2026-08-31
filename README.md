# Lost and Found Management System

[![Java CI with Maven](https://github.com/myrkmr/lostfound/actions/workflows/maven.yml/badge.svg)](https://github.com/myrkmr/lostfound/actions/workflows/maven.yml)
[![Coverage Status](https://coveralls.io/repos/github/myrkmr/lostfound/badge.svg?branch=main)](https://coveralls.io/github/myrkmr/lostfound?branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=myrkmr_lostfound&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=myrkmr_lostfound)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=myrkmr_lostfound&metric=coverage)](https://sonarcloud.io/summary/new_code?id=myrkmr_lostfound)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=myrkmr_lostfound&metric=bugs)](https://sonarcloud.io/summary/new_code?id=myrkmr_lostfound)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=myrkmr_lostfound&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=myrkmr_lostfound)

A Java 17 desktop application that records and manages lost items.

Users can add a lost item, view all recorded items, and delete an existing item.

The user interface is developed with Java Swing, while MongoDB is used for data storage. The application follows the model-view-controller and repository design patterns.

The project was developed using test-driven development. Maven manages the build, and Docker provides MongoDB containers for integration and end-to-end testing.

Code quality is checked through GitHub Actions, JaCoCo, PITest, Coveralls, and SonarCloud.
