package com.boydti.fawe.object.pattern;

import com.boydti.fawe.FaweCache;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import jdk.nashorn.internal.ir.Block;


import static com.google.common.base.Preconditions.checkNotNull;

public class IdPattern extends AbstractExtentPattern {
    private final Pattern pattern;

    public IdPattern(Extent extent, Pattern parent) {
        super(extent);
        checkNotNull(parent);
        this.pattern = parent;
    }

    @Override
    public BlockStateHolder apply(Vector position) {
        BlockStateHolder oldBlock = getExtent().getBlock(position);
        BlockStateHolder newBlock = pattern.apply(position);
        return newBlock.withPropertyId(oldBlock.getInternalPropertiesId());
    }
}