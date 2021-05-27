package code_files.entity_movement;

import code_files.entities.entities_tree.Entity;

// описывет столкновение объектов, но не его эффект

public class Collision implements code_files.interfaces.Collision {
    private Interception interception;

    private Coords currentCoords, nextTickCoords;

    private double cellHeight, cellWeight;

    private PositionInfo positionInfo;

    private Entity entity;

    private static Collision collision;
    public static Collision getCollision(double _cellHeight, double _cellWidth){
        if (collision == null){
            collision = new Collision(_cellHeight, _cellWidth);
        }
        return collision;
    }
    private Collision(double _cellHeight, double _cellWidth){
        cellHeight = _cellHeight;
        cellWeight = _cellWidth;

        interception = Interception.getInterception(_cellHeight, _cellWidth);
    }

    private void getNextTickEntity(){
        entity.moveTick();
    }
    private void returnEntityBack(){entity.moveBack();}

    private void constructInformation(){
        currentCoords = interception.getCoords(entity, cellHeight,cellWeight);

        getNextTickEntity();
        nextTickCoords = interception.getCoords(entity, cellHeight, cellWeight);

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