/**
 * @file ModConfig.java
 * @author Stefan Wilhelm (wile)
 * @copyright (C) 2018 Stefan Wilhelm
 * @license MIT (see https://opensource.org/licenses/MIT)
 *
 * Main class for module settings. Handles reading and
 * saving the config file.
**/
package wile.rsgauges;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import java.io.File;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ModRsGauges.MODID)
@Config.LangKey("rsgauges.config.title")
public class ModConfig {

  @Config.Comment("Sample interval of the gauges in ticks. Lower values decrease the display "
                + "latency for indirect weak power measurements. The value is mainly related "
                + "to the server side logic. Minor performance impact for values >= 5.")
  @Config.Name("Gauge sample interval")
  @Config.RangeInt(min=2, max=100)
  public static int gauge_update_interval = 8;

  @Config.Comment("Sample interval of volume sensing automatic switches in ticks (e.g infrared motion detector). "
      + "Lower values make the switches reacting faster, but also have an impact on the server performance due "
      + "to ray tracing.")
  @Config.Name("Volumetric sensor switch sample interval")
  @Config.RangeInt(min=5, max=50)
  public static int autoswitch_volumetric_update_interval = 10;

  @Config.Comment("Sample interval of the linear switches in ticks (like laser pointer based sensors). "
      + "Lower values make the switches reacting faster, but also have an impact on the server performance "
      + "due to ray tracing. Has much less impact as the volumetric autoswitch interval.")
  @Config.Name("Linear sensor switch sample interval")
  @Config.RangeInt(min=1, max=50)
  public static int autoswitch_linear_update_interval = 4;

  @Config.Comment("Completely disable all (power metering) gauges. Requires restart.")
  @Config.Name("Without gauges")
  @Config.RequiresMcRestart
  public static boolean without_gauges = false;

  @Config.Comment("Completely disable all (blinking and steady) indicator lamps/LEDs. Requires restart.")
  @Config.Name("Without indicators")
  @Config.RequiresMcRestart
  public static boolean without_indicators = false;

  @Config.Comment("Completely disable all blinking indicator lamps/LEDs. Requires restart.")
  @Config.Name("Without blinking indicators")
  @Config.RequiresMcRestart
  public static boolean without_blinking_indicators = false;

  @Config.Comment("Completely disable all sound emmitting indicators. Requires restart.")
  @Config.Name("Without blinking indicators")
  @Config.RequiresMcRestart
  public static boolean without_sound_indicators = false;

  @Config.Comment("Completely disable all (button like) pulse switches. Requires restart.")
  @Config.Name("Without pulse switches")
  @Config.RequiresMcRestart
  public static boolean without_pulse_switches = false;

  @Config.Comment("Completely disable all (lever like) bistable switches. Requires restart.")
  @Config.Name("Without bistable switches")
  @Config.RequiresMcRestart
  public static boolean without_bistable_switches = false;

  @Config.Comment("Completely disable all contact switches. Requires restart.")
  @Config.Name("Without contact switches")
  @Config.RequiresMcRestart
  public static boolean without_contact_switches = false;

  @Config.Comment("Completely disable all automatic switches. Requires restart.")
  @Config.Name("Without automatic switches")
  @Config.RequiresMcRestart
  public static boolean without_automatic_switches = false;

  @Config.Comment("Completely disable all decorative blocks. Requires restart.")
  @Config.Name("Without decorative blocks")
  @Config.RequiresMcRestart
  public static boolean without_decorative = false;

  @Config.Comment("Comma sepatated list of items names that can be used alter (NBT) configurable blocks of this mod." +
                  "This applies when the display side of the block is right click (activated) with the item in the " +
                  "main hand. Empty hand is 'air'.")
  @Config.Name("Accepted wrenches")
  public static String accepted_wrenches = "air,redstone_torch";

  @Config.Comment("Timeout in milliseconds defining the timeout for left clicking switches or devices in order to " +
                  "configure them. If the device can be opened, it will be opened on 'double-left-click' and closed " +
                  "again on 'single-left-click'. The item in the hand must be a valid wrench (see 'Accepted wrenches'). " +
                  "For switches/devices that cannot be opened, multi-clicking cycles through the configuration options. " +
                  "The block has to be at least clicked two times withing the timeout to differ configuration from block " +
                  "breaking, and prevent misconfiguration on unintended left-clicking.")
  @Config.Name("Config left multi-click timeout")
  public static int config_left_click_timeout = 700;


  @Mod.EventBusSubscriber(modid=ModRsGauges.MODID)
  private static final class EventHandler {
    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
      if(!event.getModID().equals(ModRsGauges.MODID)) return;
      ConfigManager.sync(ModRsGauges.MODID, Config.Type.INSTANCE);
      update();
    }
  }

  public static final void onPostInit(FMLPostInitializationEvent event) { update(); }

  private static final void update() {}
}
