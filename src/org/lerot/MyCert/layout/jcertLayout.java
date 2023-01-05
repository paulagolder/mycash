/*
 * Created on 17-Jan-2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.lerot.MyCert.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * A layoutManager which stacks components one on top of the other, regardless
 * of their size.
 * 
 * @version Improved by Mark Grand
 */
public abstract class jcertLayout implements LayoutManager
{

	class settings extends HashMap
	{

		private static final long serialVersionUID = 1L;

		public settings()
		{
			super();
		}

		public int getInteger(String key)
		{
			if (!containsKey(key))
				return -1;
			if (get(key) == null)
				return -1;
			return ((Integer) get(key)).intValue();
		}

		public boolean isTrue(String key)
		{
			if (!containsKey(key))
				return false;
			if (get(key) == null)
				return false;
			int v = ((Integer) get(key)).intValue();
			if (v < 1)
				return false;
			else
				return true;
		}

		public void putBoolean(String key, boolean value)
		{
			if (value)
				putInt(key, 1);
			else
				putInt(key, 0);
		}

		public void putInt(String key, int value)
		{
			put(key, new Integer(value));
		}

	}

	static Hashtable codeTable = new Hashtable();

	/**
	 * Create a jcertLayout with a default vertical gap.
	 */

	@Override
	public void addLayoutComponent(String tag, Component comp)
	{
		settings nsetting = new settings();
		tag = tag.toUpperCase().trim();
		//System.out.println("jcertLayout: adding\"" + tag + "\"");
		int harg = 0, varg = 0;
		int i, l = tag.length(), n;
		for (i = 0; i < l;)
		{
			if (tag.startsWith("CENTER", i))
			{
				i += 6;
				nsetting.putBoolean("CENTER", true);
			} else if (tag.startsWith("MIDDLE", i))
			{
				i += 6;
				nsetting.putBoolean("MIDDLE", true);
			} else if (tag.startsWith("LEFT", i))
			{
				i += 4;
				nsetting.putBoolean("LEFT", true);
			} else if (tag.startsWith("TOP", i))
			{
				i += 3;
				nsetting.putBoolean("TOP", true);
			} else if (tag.startsWith("RIGHT", i))
			{
				i += 5;
				nsetting.putBoolean("RIGHT", true);
			} else if (tag.startsWith("BOTTOM", i))
			{
				i += 6;
				nsetting.putBoolean("BOTTOM", true);
			} else if (tag.startsWith("WIDE", i))
			{
				i += 4;
				if (tag.startsWith("*", i))
				{
					i++;
					n = countDigits(tag, i);
					harg = parseArg(tag, i, n);
					i += n;
				} else
					harg = 0;
				nsetting.putInt("WIDE", harg);
			} else if (tag.startsWith("TALL", i))
			{
				i += 4;
				if (tag.startsWith("*", i))
				{
					i++;
					n = countDigits(tag, i);
					varg = parseArg(tag, i, n);
					i += n;
				} else
					varg = 0;
				nsetting.putInt("TALL", varg);
			} else if (tag.startsWith("FILLW", i))
			{
				i += 5;
				harg = 1;
				nsetting.putBoolean("FILLW", true);
			} else if (tag.startsWith("FILLH", i))
			{
				i += 5;
				varg = 1;
				nsetting.putBoolean("FILLH", true);
			} else if (tag.startsWith("FILLB", i))
			{
				i += 5;
				if (tag.startsWith("*", i))
				{
					i++;
					n = countDigits(tag, i);
					harg = varg = parseArg(tag, i, n);
					i += n;
				} else
					harg = varg = 1;
				nsetting.putBoolean("FILLW", true);
				nsetting.putBoolean("FILLH", true);
			}
			else if (tag.startsWith("COLSPAN", i))
			{
				i += 7;
				if (tag.startsWith("=", i))
				{
					i++;
					n = countDigits(tag, i);
					harg = parseArg(tag, i, n);
					i += n;
					nsetting.putInt("COLSPAN", harg);
				} else
				{
					harg = -1;
					break;
				}
			}
			else if (tag.startsWith("ROWSPAN", i))
			{
				i += 7;
				if (tag.startsWith("=", i))
				{
					i++;
					n = countDigits(tag, i);
					harg = parseArg(tag, i, n);
					i += n;
					nsetting.putInt("ROWSPAN", harg);
				} else
				{
					harg = -1;
					break;
				}
			}
			else if (tag.startsWith("COL", i))
			{
				
				i += 3;
				if (tag.startsWith("=", i))
				{
					i++;
					n = countDigits(tag, i);
					harg = parseArg(tag, i, n);
					i += n;
					nsetting.putInt("COL", harg);
					//System.out.println(" setting cell COL = "+harg);
				} else
				{
					harg = -1;
					break;
				}
			}
			else if (tag.startsWith("ROW", i))
			{
				i += 3;
				if (tag.startsWith("=", i))
				{
					i++;
					n = countDigits(tag, i);
					harg = parseArg(tag, i, n);
					i += n;
					nsetting.putInt("ROW", harg);
				} else
				{
					harg = -1;
					break;
				}
			}
			
			else if (tag.startsWith("WIDTH", i))
			{
				i += 5;
				if (tag.startsWith("=", i))
				{
					i++;
					n = countDigits(tag, i);
					harg = parseArg(tag, i, n);
					i += n;
					nsetting.putInt("WIDTH", harg);
				} else
				{
					harg = -1;
					break;
				}
			} else if (tag.startsWith("HEIGHT", i))
			{
				i += 6;
				if (tag.startsWith("=", i))
				{
					i++;
					n = countDigits(tag, i);
					varg = parseArg(tag, i, n);
					i += n;
					nsetting.putInt("HEIGHT", varg);
				} else
				{
					varg = -1;
					break;
				}
			}

			else if (tag.startsWith("FLUSH", i))
			{
				i += 5;
				nsetting.putBoolean("FLUSHV", true);
				nsetting.putBoolean("FLUSHH", true);
			} else
			{
				harg = -1;
				break;
			}
			for (; (i < l) && Character.isWhitespace(tag.charAt(i)); i++)
				; // skip
			// whitesp.
		}
		if ((harg == -1) || (varg == -1))
			System.out.println("jcertLayout: can't understand \"" + tag + "\"");
		else
		{
			// System.out.println("jcertLayout:" + tag + " adding " +
			// comp.getName()
			// + nsetting.toString());
			codeTable.put(comp, nsetting);
		}
	}

	int countDigits(String tag, int i)
	{
		int j, l = tag.length();
		for (j = i; (j < l) && Character.isDigit(tag.charAt(j)); j++)
			;
		return j - i;
	}

	public  settings getSettings(Component comp)
	{
		settings s = (settings) codeTable.get(comp);
		if (s == null)
			s = new settings();
		return s;

	}

	public Dimension useMinimum(Component comp, boolean usemin)
	{
		if (usemin)
			return comp.getMinimumSize();
		else
			return comp.getPreferredSize();
	}

	public boolean isSettingTrue(Component comp, String v)
	{
		settings s = (settings) codeTable.get(comp);
		return s.isTrue(v);
	}

	@Override
	public abstract void layoutContainer(Container parent);

	@Override
	public abstract Dimension minimumLayoutSize(Container parent);

	int parseArg(String tag, int i, int n)
	{
		int num = -1;
		try
		{
			num = Integer.parseInt(tag.substring(i, i + n));
		} catch (Exception e)
		{
		}
		return num;
	}

	@Override
	public abstract Dimension preferredLayoutSize(Container parent);

	/** Remove the specified component from the layout. */
	@Override
	public void removeLayoutComponent(Component comp)
	{
		codeTable.remove(comp);
	}

	
} // class jcertLayout
