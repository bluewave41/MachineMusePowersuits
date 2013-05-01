package net.machinemuse.powersuits.client.render.modelspec

import net.machinemuse.general.geometry.Colour
import net.machinemuse.general.MuseLogger
import net.minecraft.item.ItemStack
import net.machinemuse.powersuits.item.ItemPowerArmor
import net.machinemuse.utils.MuseStringUtils
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 9:11 AM, 29/04/13
 */
object DefaultModelSpec {
  val normalcolour: Colour = Colour.WHITE
  val glowcolour: Colour = new Colour(17.0 / 255, 78.0 / 255, 1, 1)
  val tex = "/mods/mmmPowersuits/textures/models/diffuse.png"

  def loadDefaultModel: Option[ModelSpec] = {
    loadModel("/mods/mmmPowersuits/models/mps_helm.obj", tex.split(";")).map(model => {
      makeEntries(Head, 0, normalcolour, false, "helm_main;helm_tube_entry1;helm_tubes;helm_tube_entry2".split(";"), model)
      makeEntries(Head, 0, glowcolour, true, "visor".split(";"), model)
      model
    })
    loadModel("/mods/mmmPowersuits/models/mps_arms.obj", tex.split(";")).map(model => {
      makeEntries(RightArm, 1, normalcolour, false, "arms3".split(";"), model)
      makeEntries(RightArm, 1, glowcolour, true, "crystal_shoulder_2".split(";"), model)
      makeEntries(LeftArm, 1, normalcolour, false, "arms2".split(";"), model)
      makeEntries(LeftArm, 1, glowcolour, true, "crystal_shoulder_1".split(";"), model)
      model
    })
    loadModel("/mods/mmmPowersuits/models/mps_chest.obj", tex.split(";")).map(model => {
      makeEntries(Body, 1, normalcolour, false, "belt;chest_main;polySurface36;backpack;chest_padding".split(";"), model)
      makeEntries(Body, 1, glowcolour, true, "crystal_belt".split(";"), model)
      model
    })
    loadModel("/mods/mmmPowersuits/models/mps_pantaloons.obj", tex.split(";")).map(model => {
      makeEntries(RightLeg, 2, normalcolour, false, "leg1".split(";"), model)
      makeEntries(LeftLeg, 2, normalcolour, false, "leg2".split(";"), model)
      model
    })
    loadModel("/mods/mmmPowersuits/models/mps_boots.obj", tex.split(";")).map(model => {
      makeEntries(RightLeg, 3, normalcolour, false, "boots1".split(";"), model)
      makeEntries(LeftLeg, 3, normalcolour, false, "boots2".split(";"), model)
      model
    })
  }

  def loadModel(file: String, textures: Array[String]): Option[ModelSpec] = {
    ModelRegistry.loadModel(file) match {
      case Some(m) => Some(ModelRegistry.put(MuseStringUtils.extractName(file), new ModelSpec(m, textures, None, None, file)))
      case None => MuseLogger.logError("Model file " + file + " not found! D:")
    }
  }

  def makeEntries(target: MorphTarget, slot: Int, colour: Colour, glow: Boolean, names: Array[String], model: ModelSpec) {
    for (name <- names) {
      model.put(name, new ModelPartSpec(model, target, name, slot, colour, glow, name))
    }
  }

  def makeModelPrefs(stack: ItemStack, slot: Int): NBTTagList = {
    val item = stack.getItem().asInstanceOf[ItemPowerArmor]
    val normalcolour = item.getColorFromItemStack(stack)
    val glowcolour = item.getGlowFromItemStack(stack)
    val list = slot match {
      case 0 => {
        makePrefs("mps_helm", "helm_main;helm_tube_entry1;helm_tubes;helm_tube_entry2".split(";"), normalcolour, false) ++
          makePrefs("mps_helm", "visor".split(";"), glowcolour, true)
      }
      case 1 => {
        makePrefs("mps_arms", "arms2;arms3".split(";"), normalcolour, false) ++
          makePrefs("mps_arms", "crystal_shoulder_2;crystal_shoulder_1".split(";"), glowcolour, true) ++
          makePrefs("mps_chest", "belt;chest_main;polySurface36;backpack;chest_padding".split(";"), normalcolour, false) ++
          makePrefs("mps_chest", "crystal_belt".split(";"), glowcolour, true)
      }
      case 2 => {
        makePrefs("mps_pantaloons", "leg1;leg2".split(";"), normalcolour, false)
      }
      case 3 => {
        makePrefs("mps_boots", "boots1;boots2".split(";"), normalcolour, false)
      }

    }
    (new NBTTagList() /: list) {
      case (taglist, elem) => taglist appendTag elem; taglist
    }
  }

  def makePrefs(modelname: String, partnames: Array[String], colour: Colour, glow: Boolean): Array[NBTTagCompound] = {
    ModelRegistry.get(modelname).map(model =>
      for (name <- partnames) yield makePref(model.get(name).get, Some(colour), Some(glow))
    ) getOrElse Array.empty[NBTTagCompound]
  }

  def makePref(partSpec: ModelPartSpec, colour: Option[Colour], glow: Option[Boolean]): NBTTagCompound = {
    partSpec.multiSet(new NBTTagCompound(), None, glow, colour)
  }
}
