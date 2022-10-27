package dev.lone.itemsadder.Utils.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;

public class Events
{
    static final String ORG_BUKKIT = "org.bukkit";
    static PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();

    /**
     * Register event listener class only if it's not already registered.
     *
     * @param listener The listener class.
     * @param plugin   The plugin which owns this class.
     */
    public static void registerOnce(Listener listener, Plugin plugin)
    {
        for (RegisteredListener registeredListener : HandlerList.getRegisteredListeners(plugin))
            if (listener.getClass().isInstance(registeredListener.getListener())) return;
        PLUGIN_MANAGER.registerEvents(listener, plugin);
    }

    /**
     * Register event listener class without first checking if it's already registered or not.
     *
     * @param listener The listener class.
     * @param plugin   The plugin which owns this class.
     */
    public static void register(Listener listener, Plugin plugin)
    {
        PLUGIN_MANAGER.registerEvents(listener, plugin);
    }

    /**
     * Unregisters event listener class.
     *
     * @param listener The listener class to unregister.
     */
    public static void unregister(Listener listener)
    {
        HandlerList.unregisterAll(listener);
    }

    /**
     * Triggers an event.
     *
     * @param e The event to be called.
     * @return true if not cancelled, false if cancelled.
     */
    public static boolean call(Event e)
    {
        PLUGIN_MANAGER.callEvent(e);
        if (e instanceof Cancellable) return !((Cancellable) e).isCancelled();
        return true;
    }

    /**
     * Check if an event is fake or not.
     * Useful to skip fake events used by some plugins as permission checking hack (example: ItemsAdder, mcMMO and other).
     *
     * @param e   The event to check.
     * @param <T> The event clazz.
     * @return true if fake, false if not.
     */
    public static <T extends BlockBreakEvent> boolean isFake(T e)
    {
        return e.getClass() != BlockBreakEvent.class;
    }

    /**
     * Check if an event is fake or not.
     * Useful to skip fake events used by some plugins as permission checking hack (example: ItemsAdder, mcMMO and other).
     *
     * @param e   The event to check.
     * @param <T> The event clazz.
     * @return true if fake, false if not.
     */
    public static <T extends BlockPlaceEvent> boolean isFake(T e)
    {
        Class<? extends BlockPlaceEvent> clazz = e.getClass();
        return !(clazz == BlockPlaceEvent.class || clazz == BlockMultiPlaceEvent.class);
    }

    /**
     * Check if an event is fake or not.
     * Useful to skip fake events used by some plugins as permission checking hack (example: ItemsAdder, mcMMO and other).
     *
     * @param e   The event to check.
     * @param <T> The event clazz.
     * @return true if fake, false if not.
     */
    public static <T extends EntityDamageByEntityEvent> boolean isFake(T e)
    {
        return e.getClass() != EntityDamageByEntityEvent.class;
    }

    /**
     * Check if an event is fake or not.
     * Useful to skip fake events used by some plugins as permission checking hack (example: ItemsAdder, mcMMO and other).
     *
     * @param e   The event to check.
     * @param <T> The event clazz.
     * @return true if fake, false if not.
     */
    public static <T extends FoodLevelChangeEvent> boolean isFake(T e)
    {
        return e.getClass() != FoodLevelChangeEvent.class;
    }

    /**
     * Check if an event is fake or not.
     * Useful to skip fake events used by some plugins as permission checking hack (example: ItemsAdder, mcMMO and other).
     *
     * Slower than the other implementations.
     * Not really slow but it's not suitable for extremely expensive events.
     * DO NOT USE IT ON BlockPhysicsEvent and similar TO AVOID CAUSING USELESS PROCESSING!
     *
     * @param e   The event to check.
     * @param <T> The event clazz.
     * @return true if fake, false if not.
     */
    @Deprecated
    static <T extends Event> boolean isFake(T e)
    {
        return !e.getClass().getPackage().getName().startsWith(ORG_BUKKIT);
    }
}
