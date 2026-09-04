# 03 - Design (Sprint 1)

## Database schema

### Bikes Table
- id: number, unic identifier, auto generated
- brand: text
- color : text
- description: text max 50 char
- date: date
- latitude: number
- longitude: number
- address: text

### bike_photos table
- id: number, unic identifier, auto generated
- bike_id: number (links to bikes table)
- file_path: text ( path/filename of the photo on the server )

## API endpoints
GET /api/bikes -> return the list of all bikes
POST /api/bikes -> add a new bike (receive form data, saves on database)

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