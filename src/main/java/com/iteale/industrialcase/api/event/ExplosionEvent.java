package com.iteale.industrialcase.api.event;


import com.mojang.math.Vector3d;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Cancelable;


@Cancelable
public class ExplosionEvent extends WorldEvent {
	public ExplosionEvent(Level world, Entity entity,
						  Vector3d pos,
						  double power,
						  LivingEntity igniter,
						  int radiationRange, double rangeLimit) {
		super(world);

		this.entity = entity;
		this.pos = pos;;
		this.power = power;
		this.igniter = igniter;
		this.radiationRange = radiationRange;
		this.rangeLimit = rangeLimit;
	}

	/**
	 * Entity representing the explosive, may be null.
	 */
	public final Entity entity;
	public final Vector3d pos;
	public final double power;
	/**
	 * Entity causing the explosion, may be null.
	 */
	public final LivingEntity igniter;
	public final int radiationRange;
	public final double rangeLimit;
}
