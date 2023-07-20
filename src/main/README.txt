Characters are comprised of 2 pieces of information: data and image.
Character data is stored in data/game/CharcterData.json.
Character images are stored (currently) in data/game freely, with character data storing the images' file names.
NetLoader stores a path to 'data/game/' which the file names are concatenated with to create paths.

An ID is associated with each character, with ID: 0 reserved for testing.

Character data is loaded as an ArrayList of Person's in persistence.NetLoader with indices corresponding ID's.


Page Navigation: Keep Page references in a list and assign each page a next, prev field.