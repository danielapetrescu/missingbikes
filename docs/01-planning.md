# 01 - Planning

## Purpose

The site helps people who lost their bikes find them again, even if the bike ends up in a different neighbourhood — by
letting anyone who finds an abandoned/lost bike post it with a photo and location.

## Backlog (identified features )

1. add a bike + color + photo + location
2. add the frame number and bikes that have the frame number will have the location hide so only the owner can see it
3. list of bikes
4. search of bike

## Sprint 1

- Add bike (photo + location + description)
- List of bikes found

## Definition of Done (Sprint 1 )

- The application runs locally
- You can add a bike with photo and location
- You can see the bike in list
- Code is in GitHub

## Sprint 2
- search by brand and color

### Technical tasks
Rezolv each point from review:
 - Encode placeId with encodeURIComponent in add-bike.html                                                                                                                                                                        
  - Return 201 Created from POST /api/brands                                                                                                                                                                                       
  - Keep only one way of listing brands (sorted)                                                                                                                                                                                   
  - Create the new Brand only after the duplicate check                                                                                                                                                                            
                                                                                                                                                                                                                                   
  Review                                                                                                                                                                                                                           
  - Review the code at the end of the sprint                                                                                                                                                                                       
  - Make sure no controller returns a JPA entity; JSON responses use DTOs    

## Definition of Done (Sprint 2 )
- you can search by Brand and Color
- The code has been reviewed                                                                                                                                                                                                     
- No controller returns a JPA entity  
- code is in GitHub