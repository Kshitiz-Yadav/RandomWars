package com.example.randomwars.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.resources.SpriteSheet;

import static com.example.randomwars.map.TileLayout.NUMBER_OF_COLUMN_TILES;
import static com.example.randomwars.map.TileLayout.NUMBER_OF_ROW_TILES;
import static com.example.randomwars.map.TileLayout.TILE_HEIGHT_PIXELS;
import static com.example.randomwars.map.TileLayout.TILE_WIDTH_PIXELS;

public class TileMap {

    private TileLayout tileLayout;
    private SpriteSheet spriteSheet;
    private Tile[][] tileMap;
    private Bitmap mapBitmap;

    public TileMap(SpriteSheet spriteSheet) {
        tileLayout = new TileLayout();
        this.spriteSheet = spriteSheet;
        createTileMap();
    }

    private void createTileMap(){
        int[][] layout = tileLayout.getTileLayout();
        tileMap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for(int i=0;i<NUMBER_OF_ROW_TILES;i++){
            for(int j=0;j<NUMBER_OF_COLUMN_TILES;j++){
                tileMap[i][j] = Tile.getTile(
                  layout[i][j] - 1,
                  getRectByIndex(i, j),
                  spriteSheet
                );
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
                NUMBER_OF_COLUMN_TILES*TILE_WIDTH_PIXELS,
                NUMBER_OF_ROW_TILES*TILE_HEIGHT_PIXELS,
                config
        );

        Canvas mapCanvas = new Canvas(mapBitmap);

        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol++) {
                tileMap[iRow][iCol].draw(mapCanvas);
            }
        }
    }

    private Rect getRectByIndex(int i, int j) {
        return new Rect(
                j * TILE_WIDTH_PIXELS,
                i * TILE_HEIGHT_PIXELS,
                (j + 1) * TILE_WIDTH_PIXELS,
                (i + 1) * TILE_HEIGHT_PIXELS
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(
                mapBitmap,
                gameDisplay.getGameRect(),
                gameDisplay.displayRect,
                null
        );
    }
}
