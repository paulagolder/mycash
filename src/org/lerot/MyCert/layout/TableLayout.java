package org.lerot.MyCert.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Vector;

import org.lerot.MyCert.utils;

public class TableLayout extends jcertLayout {

	private class rowcol {
		protected double size;
		protected double position;
	}

	private static final int DEFAULT_HGAP = 0;
	private int hgap;
	private static final int DEFAULT_VGAP = 0;
	public static final int CENTER = 0;
	private int vgap;

	private boolean changed = true;
	private Vector<rowcol> columns;
	private Vector<rowcol> rows;
	int maxrow = 0;
	int maxcol = 0;
	int desiredwidth = -1;
	int desiredheight = -1;

	public TableLayout() {
		this(DEFAULT_HGAP, DEFAULT_VGAP);
	}

	public TableLayout(int hgap, int vgap) {
		this.hgap = hgap;
		this.vgap = vgap;
		rows = new Vector<rowcol>();
		columns = new Vector<rowcol>();
	}

	public void setRowCols(Container parent) {
		rows.clear();
		columns.clear();
		maxrow = 0;
		maxcol = 0;
		int ncomponents = parent.getComponentCount();
		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			if (comp.isVisible() && comp instanceof jcertPanel) {
				settings s = getSettings(comp);
				int ncol = s.getInteger("COL");
				int nrow = s.getInteger("ROW");
				if (ncol > maxcol)
					maxcol = ncol;
				if (nrow > maxrow)
					maxrow = nrow;
			}
		}
		maxcol++;
		maxrow++;
		rows.setSize(maxrow);
		columns.setSize(maxcol);
		for (int nrow = 0; nrow < maxrow; nrow++) {
			rowcol rset = new rowcol();
			rset.size = 0;
			rset.position = 0;
			rows.set(nrow, rset);
		}
		for (int ncol = 0; ncol < maxcol; ncol++) {
			rowcol cset = new rowcol();
			cset.size = 0;
			cset.position = 0;
			columns.set(ncol, cset);
		}
		// System.out.println( " setting maxrow="+maxrow+" maxcol="+maxcol);

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			if (comp.isVisible() && comp instanceof jcertPanel) {
				settings s = getSettings(comp);
				jcertPanel jccomp = (jcertPanel) comp;
				Dimension min = jccomp.getMinimumSize();
				int ncol = s.getInteger("COL");
				int nrow = s.getInteger("ROW");
				int colspan = s.getInteger("COLSPAN");
				int rowspan = s.getInteger("ROWSPAN");
				int width = s.getInteger("WIDTH");
				if (width > min.width) {
					min.width = width;
					System.out.println(" Setting width =" + width);
				}
				if (ncol > -1 && nrow > -1) 
				{
					if(rowspan<2)
					{
					rowcol rset = rows.get(nrow);
					if (rset.size < min.height ) {
						rset.size = min.height ;
						rows.set(nrow, rset);
					}
					}
					if (colspan > 1) {

						double sharedwidth = min.width / colspan;
						for (int c = ncol; c < ncol + colspan; c++) {
							rowcol cset = columns.get(c);
							if (cset.size < sharedwidth) {
								cset.size = sharedwidth;
								columns.set(c, cset);
							}
						}
					} else {
						rowcol cset = columns.get(ncol);
						// if (cset != null)
						{
							// paul to fix
							if (cset.size < min.width) {
								cset.size = min.width;
								columns.set(ncol, cset);
							}
						}
					}
				}
			}
		}

		int rpos = 0;

		for (int nrow = 0; nrow < maxrow; nrow++) {
			rowcol rset = rows.get(nrow);
			if (rset != null) {
				rset.position = rpos;
				rows.set(nrow, rset);
				rpos += rset.size;
			}
		}
		desiredheight = rpos;
		int cpos = 0;

		for (int ncol = 0; ncol < maxcol; ncol++) {
			rowcol cset = columns.get(ncol);
			if (cset != null) {
				cset.position = cpos;
				columns.set(ncol, cset);
				cpos += cset.size;
			}
		}
		desiredwidth = cpos;
	}

	@Override
	public void layoutContainer(Container parent) {
		// if (changed)
		setRowCols(parent);
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0) {
			this.hgap = 0;
			this.vgap = 0;
		}
		Insets insets = parent.getInsets();

		Dimension dd = this.minimumLayoutSize(parent);
		Dimension parentSize = parent.getParent().getSize();
		int usableWidth = parentSize.width - insets.left - insets.right;
		double cscale = usableWidth / (float) dd.width;
		// cscale=1;
		int availableHeight = parentSize.height - insets.top - insets.bottom;
		double rscale = 1.0;
		if (dd.height > availableHeight)
			rscale = availableHeight / (float) dd.height;
		rscale = 1;
		// System.out.println(" insets " + parent.getName() + " pw= "
		// + usableWidth + " hgap= " + hgap
		// + " cscale=" + cscale );
		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			settings s = getSettings(comp);
			if (comp.isVisible()) {
				int ncol = s.getInteger("COL");
				int nrow = s.getInteger("ROW");
				int colspan = s.getInteger("COLSPAN");
				int rowspan = s.getInteger("ROWSPAN");
				// Dimension d = useMinimum(comp, useMinimum);
				rowcol rowsettings = rows.get(nrow);
				int y = (int) rowsettings.position + insets.top;
				int height = (int) rowsettings.size;
				if (rowspan > 1) {
					height = 0;
					for (int r = nrow; r < nrow + rowspan; r++) {
						rowcol rowsettingsr = rows.get(r);
						height += (int) rowsettingsr.size;
					}

				}
				rowcol colsettings = columns.get(ncol);
				int x = (int) colsettings.position + insets.left;
				int width = 0;
				if (colspan > 1) {
					// System.out.println("spanning cols from" + ncol + " to "
					// + (ncol + colspan));
					for (int c = 0; c < colspan; c++) {
						width += (int) columns.get(ncol + c).size;
					}
					// width = (int) colsettings.size;

				} else
					width = (int) colsettings.size;
				comp.setBounds((int) (x * cscale), (int) (y * rscale), (int) (width * cscale), (int) (height * rscale));
				// comp.setLocation((int) (x * cscale), (int) (y * rscale));
				int pborderwidth = ((TablePanel) parent).getCellborderwidth();
				if (pborderwidth > 0) {
					((jcertPanel) comp)
							.setBorder(utils.setborder(((TablePanel) parent).getCellbordercolor(), pborderwidth));
				}
				// ((jcertPanel) comp).layoutContainer();
				// System.out.println(
				// " cell "+comp.getName()+" c= "+ncol+" r="+nrow+" x="+x+
				// " y="+y+" w="+width+" h="+height);
			}
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0) {
			this.hgap = 0;
			this.vgap = 0;
		}
		setRowCols(parent);
		Insets insets = parent.getInsets();

		double height = 0;
		for (rowcol r : rows) {
			height = height + r.size;
		}
		double width = 0;
		for (rowcol c : columns) {
			if (c != null)
				width = width + c.size;
		}
		return new Dimension((int) width + 5, (int) height + 5);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {

		return minimumLayoutSize(parent);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	public void changed() {
		changed = true;
	}

}
