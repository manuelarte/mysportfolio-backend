# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.24] - 2020-10-24

### Added

### Changed

- Updated some dependencies

### Removed

- Removed annotation @EnableWebMvc because it was giving problems when serializing dates

## [0.2.23] - 2020-10-24

### Added

- Adding CORS properly (tested with curl)

### Changed

### Removed

## [0.2.22] - 2020-10-24

### Added

- adding cors configuration of Spring Web

### Changed

### Removed

## [0.2.21] - 2020-10-24

### Added

### Changed

### Removed

- Disabling CORS

## [0.2.20] - 2020-10-24

### Added

- Enabling cors back

### Changed

### Removed

- Removed set port 8090

## [0.2.19] - 2020-10-24

### Added

### Changed

### Removed

- - Removing everything related to CORS

## [0.2.18] - 2020-10-24

### Added

- Disabling CORS

### Changed

### Removed

## [0.2.17] - 2020-10-24

### Added

- Adding CORS
- Adding default player profile when requesting player profile

### Changed

- Changing user matches query controller to use user external id

### Removed

## [0.2.16] - 2020-10-22

### Added

- Added endpoint to retrieve some user's information
- Added HalfTime match event

### Changed

### Removed

## [0.2.15] - 2020-08-17

### Added

- Added new badges push notification
- Updating Spring Boot
- Adding matchDate to competitions query
- Adding v-shape kit

### Changed

- Upgrading to Java 13.
- Changing Query Parameter for library
- Changing to manuelarte-validation

### Removed

## [0.2.14] - 2020-04-04

### Added

- Version is handled now in an external file, to avoid merge conflicts

### Changed

- Changed competition and sport for match type

### Removed

## [0.2.13] - 2020-03-30

### Added

- Adding PUT in Competition controller
- Adding more sports, FOOTBALL_7, FOOTBALL_6

### Changed

- CircleCI Workflow of master to upload image

### Removed

- Removing sport type

## [0.2.12] - 2020-03-08

### Added
- Adding CHANGELOG.

### Changed
- Fixing bug of query criteria 

## [0.2.11] - 2020-03-07

### Added
- Adding permissions per entities

### Changed
- Changing CRUD permissions for entities.