package net.machinemuse.powersuits.block;

import net.machinemuse.numina.general.MuseLogger;
import net.machinemuse.numina.geometry.Colour;
import net.machinemuse.numina.tileentity.MuseTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityLuxCapacitor extends MuseTileEntity {
    public Colour colour;
    public EnumFacing side;

    public TileEntityLuxCapacitor() {
        side = EnumFacing.DOWN;
        this.colour = new Colour(0F, 0.2F, 0.9F);
    }

    public TileEntityLuxCapacitor(EnumFacing side, double red, double green, double blue) {
        this.side = side;
        this.colour = new Colour((float)red, (float)green, (float)blue);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        super.writeToNBT(nbt);
        nbt.setInteger("s", side.ordinal());
        nbt.setInteger("c", colour.getInt());
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("s")) {
            side = EnumFacing.values()[nbt.getInteger("s")];
        } else {
            MuseLogger.logDebug("No NBT found! D:");
        }
        if (nbt.hasKey("c")) {
            colour = new Colour(nbt.getInteger("c"));
        } else {
            MuseLogger.logDebug("No NBT found! D:");
        }
    }
}