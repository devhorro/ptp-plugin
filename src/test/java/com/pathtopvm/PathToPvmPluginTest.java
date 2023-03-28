package com.pathtopvm;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PathToPvmPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PathToPvmPlugin.class);
		RuneLite.main(args);
	}
}