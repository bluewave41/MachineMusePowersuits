package net.machinemuse.powersuits.powermodule.energy;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.electricity.ElectricConversions;
import net.machinemuse.general.gui.MuseIcon;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by leon on 7/3/16.
 */
public class UltimateBatteryModule extends PowerModuleBase {
    public static final String MODULE_BATTERY_ULTIMATE = "Ultimate Battery";

    public UltimateBatteryModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.evcapacitor, 1));
        addBaseProperty(ElectricItemUtils.MAXIMUM_ENERGY, 1250000, "J");
        addBaseProperty(MuseCommonStrings.WEIGHT, 1500, "g");
        addTradeoffProperty("Battery Size", ElectricItemUtils.MAXIMUM_ENERGY, 4250000);
        addTradeoffProperty("Battery Size", MuseCommonStrings.WEIGHT, 6000);
        addBaseProperty(ElectricConversions.IC2_TIER, 1);
        addTradeoffProperty("IC2 Tier", ElectricConversions.IC2_TIER, 3);
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_ENERGY;
    }

    @Override
    public String getDataName() {
        return MODULE_BATTERY_ULTIMATE;
    }

    @Override
    public String getUnlocalizedName() {
        return "ultimateBattery";
    }

    @Override
    public TextureAtlasSprite getIcon(ItemStack item) {
        return MuseIcon.ultimateBattery;
    }
}