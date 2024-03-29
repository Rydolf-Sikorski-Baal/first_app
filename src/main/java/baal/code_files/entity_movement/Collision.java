package baal.code_files.entity_movement;

import baal.code_files.entities.entities_tree.Entity;
import org.springframework.stereotype.Component;

// описывет столкновение объектов, но не его эффект

@Component
public class Collision implements baal.code_files.blocks.Collision {
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
        currentCoords = interception.getCords(entity);

        getNextTickEntity();
        nextTickCoords = interception.getCords(entity);

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