package com.iteale.industrialcase.api.tile;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/**
 * Interface for {@link BLockEntity}s that can have rotors, see also {@link IKineticRotor}<br/>
 * Use the {@link RotorRegistry} to use IC2's default windmill renderer
 */
public interface IRotorProvider {
	/**
	 * @return Radius of current rotor (in blocks), or 0 for no rotor
	 */
	public int getRotorDiameter();

	/**
	 * @return The current direction the rotor is facing
	 */
	public Direction getFacing();

	/**
	 * @return Angle (in degrees) to render the rotor at
	 */
	public float getAngle();

	/**
	 * @return Texture of the current rotor, called every tick so remember to store in a variable
	 */
	public ResourceLocation getRotorRenderTexture();
}