/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.extent.clipboard;

import com.boydti.fawe.beta.Filter;
import com.boydti.fawe.config.Settings;
import com.boydti.fawe.object.clipboard.CPUOptimizedClipboard;
import com.boydti.fawe.object.clipboard.DiskOptimizedClipboard;
import com.boydti.fawe.object.clipboard.MemoryOptimizedClipboard;
import com.boydti.fawe.object.clipboard.ReadOnlyClipboard;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.MaskTraverser;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.extent.transform.BlockTransformExtent;
import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.function.mask.ExistingBlockMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.visitor.Order;
import com.sk89q.worldedit.function.visitor.RegionVisitor;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.MutableBlockVector2;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.Regions;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Specifies an object that implements something suitable as a "clipboard."
 */
public interface Clipboard extends Extent {

    /**
     * Get the bounding region of this extent.
     *
     * <p>Implementations should return a copy of the region.</p>
     *
     * @return the bounding region
     */
    Region getRegion();

    /**
     * Get the dimensions of the copy, which is at minimum (1, 1, 1).
     *
     * @return the dimensions
     */
    BlockVector3 getDimensions();

    /**
     * Get the origin point from which the copy was made from.
     *
     * @return the origin
     */
    BlockVector3 getOrigin();

    /**
     * Set the origin point from which the copy was made from.
     *
     * @param origin the origin
     */
    void setOrigin(BlockVector3 origin);

    /**
     * Returns true if the clipboard has biome data. This can be checked since {@link Extent#getBiome(BlockVector2)}
     * strongly suggests returning {@link com.sk89q.worldedit.world.biome.BiomeTypes#OCEAN} instead of {@code null}
     * if biomes aren't present. However, it might not be desired to set areas to ocean if the clipboard is defaulting
     * to ocean, instead of having biomes explicitly set.
     *
     * @return true if the clipboard has biome data set
     */
    default boolean hasBiomes() {
        return false;
    }
}
