# 03 - Design (Sprint 1)

## Database schema

The schema is created by Flyway migrations in `src/main/resources/db/migration`
(V1 creates the tables, V2 seeds the brands). Hibernate only validates it.

### bikes table
- id: number, unique identifier, auto generated
- brand_id: number (links to brands table)
- color: text, max 50 char
- description: text, max 50 char, optional
- date: date
- latitude: number
- longitude: number
- address: text

### brands table
- id: number, unique identifier, auto generated
- name: text, unique (list is extensible — users can add a new brand from the Add Bike form if theirs isn't listed)

### bike_photos table
- id: number, unique identifier, auto generated
- bike_id: number (links to bikes table, photos are deleted with their bike)
- path: text (`<bike id>/<file name>`, relative to the upload directory)

## API endpoints
Used by the JavaScript on the Add Bike page. Errors are returned as JSON (ProblemDetail).

GET /api/brands -> return the list of all brands
POST /api/brands -> add a new brand
GET /api/geocode -> reverse geocode latitude/longitude into a text address ("Use my location")
GET /api/address/suggestions -> return address suggestions for a typed text (autocomplete)
GET /api/address/details -> return the coordinates of the chosen suggestion

## Page structure
GET /       -> homepage, show the list of all bikes
GET /add    -> page with form to add the new bike
POST /add   -> receive the form (multipart/form-data with photos), save the bike and
               redirect to /; on invalid input show the form again with an error message
/photos/**  -> serves the uploaded photos

## Technical decisions
- Backend framework: Spring Boot
- Build tool: Maven
- Database: PostgreSQL, schema managed by Flyway
- Base package name: dk.missingbikes
- Layers: controllers handle HTTP only, services hold the business rules and the
  calls to Google, repositories talk to the database
- Controllers never return JPA entities; API responses use DTO records (package dto)
- Services throw InvalidInputException / NotFoundException; GlobalExceptionHandler
  turns them into JSON for the API, page controllers catch them and re-render the form
- Photos are stored as files on the server (not as binary data in the
  database) — only the file path is saved in the bike_photos table
- Location is stored both as coordinates (latitude/longitude, for the
  map) and as a text address
- Description field: max 50 characters (validated in both the form
  and the backend)
- Photo upload limit: max 4 photos per bike (checked in the form only)
- Accepted photo formats: JPG/JPEG, PNG, WEBP — checked on the server by file
  extension. HEIC is not accepted because most browsers cannot display it
- Max photo size: 10 MB per photo, 45 MB per request
- Pages are server-rendered with Thymeleaf (not a separate frontend app)
- Reverse geocoding uses the Google Maps Geocoding API (key in GOOGLE_MAPS_API_KEY),
  address autocomplete uses the Google Places API (key in GOOGLE_PLACES_API_KEY);
  both are called server-side, so the keys never reach the browser
- Uploaded photos are saved under a configurable upload directory, in one
  subfolder per bike id
- The address on the homepage links to Google Maps (Maps URLs format, no API key needed)
- No fixed "Other" option for Brand or Color — the user must pick an
  existing value, or add a new brand from the form
