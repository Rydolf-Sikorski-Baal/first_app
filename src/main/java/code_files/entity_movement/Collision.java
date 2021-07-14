package code_files.entity_movement;

import code_files.entities.entities_tree.Entity;
import org.springframework.stereotype.Component;

// описывет столкновение объектов, но не его эффект

@Component
public class Collision implements code_files.interfaces.Collision {
    private final Interception interception;

    private Coords currentCoords, nextTickCoords;

    private double cellHeight, cellWeight;

    private PositionInfo positionInfo;

    private Entity entity;

    private Collision(Interception interception){
        this.interception = interception;
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