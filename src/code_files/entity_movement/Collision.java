package code_files.entity_movement;

import code_files.PointDouble;
import code_files.entities.entities_tree.Entity;
import javafx.scene.canvas.Canvas;

// описывет столкновение объектов, но не его эффект

public class Collision implements code_files.interfaces.Collision {
    Interception interception;

    Coords currentCoords, nextTickCoords;

    double cellHeight, cellWeight;

    private PositionInfo positionInfo;

    private Entity entity;

    public Collision(double _cellHeight, double _cellWidth){
        cellHeight = _cellHeight;
        cellWeight = _cellWidth;

        interception = new Interception(cellHeight, cellWeight);
    }

    private void getNextTickEntity(){
        entity.moveTick();
    }
    private void returnEntityBack(){entity.moveBack();}

    private void constructInformation(){
        currentCoords = interception.getCoords(entity);

        getNextTickEntity();
        nextTickCoords = interception.getCoords(entity);

        returnEntityBack();
    }

    private void setPositionInfo(){
        positionInfo = new PositionInfo(
                currentCoords.InnerCoords,
                currentCoords.AdjacentUpCoords,
                currentCoords.AdjacentLeftCoords,
                currentCoords.AdjacentRightCoords,
                currentCoords.AdjacentDownCoords,

                nextTickCoords.InnerCoords,
                nextTickCoords.AdjacentUpCoords,
                currentCoords.AdjacentLeftCoords,
                currentCoords.AdjacentRightCoords,
                nextTickCoords.AdjacentDownCoords);
    }

    @Override
    public PositionInfo getPositionInfo(Entity _entity, double _cellHeight, double _cellWeight) {
        entity = _entity;
        cellHeight = _cellHeight;
        cellWeight = _cellWeight;

        constructInformation();
        setPositionInfo();

        return positionInfo;
    }
}