package com.example.model;
import java.util.Random;

public class Tiles {
    
    //public, so index can be easily accessed
    public Tile[] tiles;

    //Board size, change if a bigger/smaller board is desired
    //19 is a normal 3 hex-per-side board
    private final int NUMBER_OF_HEXES = 19;

    public Tiles(){
        this.tiles = setTiles();
    }

    //getter
    public Tile[] getTiles(){
        return this.tiles;
    }

    //setter
    public void setTiles(Tile[] _tiles){
        this.tiles = _tiles;
    }

    //set up all the tiles in the gameboard
    private Tile[] setTiles(){
        //generally 19 tiles
        Tile[] tiles = new Tile[NUMBER_OF_HEXES];

        //instantiate tiles
        for (int i = 0; i < NUMBER_OF_HEXES; i++){ tiles[i] = new Tile(); }

        //set tile resources
        //19 tiles: 4 wood, 4 sheep, 4 wheat, 3 brick, 3 rock, 1 desert
        //max 4 of each:
        int numOfWood = 4;
        int numOfSheep = 4;
        int numOfWheat = 4;
        //max 3 of each:
        int numOfBrick = 3;
        int numOfRock = 3;
        //max 1:
        int numOfDesert = 1;

        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_HEXES; i++){

            //repeat until valid
            boolean valid = false;
            while (!valid){
                //generate random number 0-5 (inclusive)
                //0 is wood, 1 is sheep, 2 is wheat, 3 is brick, 4 is rock, 5 is desert
                int num = random.nextInt(6);

                //assign
                switch(num){
                    case 0:
                        //if wood, set tile to wood and subtract from number of wood left to assign
                        if (numOfWood != 0){
                            tiles[i].setResource(0);
                            numOfWood--;
                            valid = true;
                        }
                        break;

                    case 1:
                        //if sheep, set tile to sheep and subtract from number of sheep left to assign
                        if (numOfSheep != 0){
                            tiles[i].setResource(4);
                            numOfSheep--;
                            valid = true;
                        }
                        break;

                    case 2:
                        //if wheat, set tile to wheat and subtract from number of wheat left to assign
                        if (numOfWheat != 0){
                            tiles[i].setResource(3);
                            numOfWheat--;
                            valid = true;
                        }
                        break;

                    case 3:
                        //if brick, set tile to brick and subtract from number of brick left to assign
                        if (numOfBrick != 0){
                            tiles[i].setResource(1);
                            numOfBrick--;
                            valid = true;
                        }
                        break;

                    case 4:
                        //if rock, set tile to rock and subtract from number of rock left to assign
                        if (numOfRock != 0){
                            tiles[i].setResource(2);
                            numOfRock--;
                            valid = true;
                        }
                        break;

                    case 5:
                        //if desert, set tile to desert and subtract from number of desert left to assign
                        if (numOfDesert != 0){
                            tiles[i].setResource(5);
                            numOfDesert--;
                            valid = true;
                        }
                        break;
                    
                }
            }
        }

        //tile resources now all set
        //assign numbers
        //also sets non-desert tiles to: not be desolate, not be blocked
        int[] numberSequence = generateTileNumberSequence();
        for (int i = 0; i < NUMBER_OF_HEXES; i++){
            //if tile is a desert, don't assign a number
            boolean passedDesert = false;
            if (tiles[i].getResource() == 5){
                passedDesert = true;
                tiles[i].setIsBlocked(true);
            }
            else{
                //assign number
                if (!passedDesert){ tiles[i].setNumber(numberSequence[i]); }
                else { tiles[i].setNumber(numberSequence[i - 1]); }
                tiles[i].setIsBlocked(false);
            }
        }

        //set adjacent vertices (so structures can be searched off of tile number)
        tiles = setAdjVerticesForEachTile(tiles);

        return tiles;
    }

    private int[] generateTileNumberSequence(){
        //temporary solution
        //algorithm to induce randomness needs added
        return new int[] {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};
    }

    private Tile[] setAdjVerticesForEachTile(Tile[] tiles){
        //manually set each tiles vertices
        tiles[0].setAdjVertices(new int[]{0, 1, 2, 8, 9, 10});
        tiles[1].setAdjVertices(new int[]{2, 3, 4, 10, 11, 12});
        tiles[2].setAdjVertices(new int[]{4, 5, 6, 12, 13, 14}); 
        tiles[3].setAdjVertices(new int[]{7, 8, 9, 17, 18, 19}); 
        tiles[4].setAdjVertices(new int[]{9, 10, 11, 19, 20, 21}); 
        tiles[5].setAdjVertices(new int[]{11, 12, 13, 21, 22, 23}); 
        tiles[6].setAdjVertices(new int[]{13, 14, 15, 23, 24, 25}); 
        tiles[7].setAdjVertices(new int[]{16, 17, 18, 27, 28, 29}); 
        tiles[8].setAdjVertices(new int[]{18, 19, 20, 29, 30, 31}); 
        tiles[9].setAdjVertices(new int[]{20, 21, 22, 31, 32, 33}); 
        tiles[10].setAdjVertices(new int[]{22, 23, 24, 33, 34, 35}); 
        tiles[11].setAdjVertices(new int[]{24, 25, 26, 35, 36, 37}); 
        tiles[12].setAdjVertices(new int[]{28, 29, 30, 38, 39, 40}); 
        tiles[13].setAdjVertices(new int[]{30, 31, 32, 40, 41, 42}); 
        tiles[14].setAdjVertices(new int[]{32, 33, 34, 42, 43, 44}); 
        tiles[15].setAdjVertices(new int[]{34, 35, 36, 44, 45, 46}); 
        tiles[16].setAdjVertices(new int[]{39, 40, 41, 47, 48, 49}); 
        tiles[17].setAdjVertices(new int[]{41, 42, 43, 49, 50, 51});
        tiles[18].setAdjVertices(new int[]{43, 44, 45, 51, 52, 53});
        
        return tiles;
    }


    
}
