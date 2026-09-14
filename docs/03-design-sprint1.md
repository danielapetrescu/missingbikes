# 03 - Design (Sprint 1)

## Database schema

### Bikes Table
- id: number, unic identifier, auto generated
- brand_id: number (links to brands table)
- color : text
- description: text max 50 char
- date: date
- latitude: number
- longitude: number
- address: text

### brands table
- id: number, unic identifier, auto generated
- name: text, unique (list is extensible — users can add a new brand from the Add Bike form if theirs isn't listed)

### bike_photos table
- id: number, unic identifier, auto generated
- bike_id: number (links to bikes table)
- path: text ( path/filename of the photo on the server )

## API endpoints
GET /api/bikes -> return the list of all bikes
POST /api/bikes -> add a new bike (receive form data + photos as multipart/form-data, saves on database)
GET /api/brands -> return the list of all brands
POST /api/brands -> add a new brand
GET /api/geocode -> reverse geocode latitude/longitude into a text address (used when adding a bike)
GET /api/address/suggestions -> return address suggestions for a typed text (autocomplete, used when adding a bike)

## Page structure
/       -> homepage, show the list of all bikes
/add    -> page with form to add the new bike

## Technical decisions
- Backend framework: Spring Boot
- Build tool: Maven
- Database: PostgreSQL
- Base package name: dk.missingbikes
- Photos are stored as files on the server (not as binary data in the 
  database) — only the file path is saved in the bike_photostable
- Location is stored both as coordinates (latitude/longitude, for the 
  map) and as a text address (calculated via reverse geocoding, when 
  the bike is added)
- Description field: max 50 characters (validated in both the form 
  and the backend)
- Photo upload limit: max 4 photos per bike
- Accepted photo formats: JPEG, PNG, HEIC
- Max photo size: 10 MB per photo
- Pages are server-rendered with Thymeleaf (not a separate frontend app)
- Reverse geocoding is done server-side by the app calling the Google Maps 
  Geocoding API, using an API key from the GOOGLE_MAPS_API_KEY env var
- Uploaded photos are saved under a configurable upload directory, in one 
  subfolder per bike id
- No fixed "Other" option for Brand or Color — the user must pick an 
  existing value, or add a new brand from the form