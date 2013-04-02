package ch.zhaw.dynsys.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

public class GraphPanel extends ChartPanel {
	private static final long serialVersionUID = 1L;

	public GraphPanel() {
		super(null);

		// data sets
		YIntervalSeriesCollection xydataset = new YIntervalSeriesCollection();
		YIntervalSeries zucker = new YIntervalSeries("Zucker");
		YIntervalSeries hefe = new YIntervalSeries("Hefe");
		YIntervalSeries baecker = new YIntervalSeries("Bäcker");
		xydataset.addSeries(zucker);
		xydataset.addSeries(hefe);
		xydataset.addSeries(baecker);

		for (int i = 0; i < 60; i++) {
			zucker.add(i, 1000 - Math.pow(2, i), 0, 100);
			hefe.add(i, Math.pow(2, i) - i * 4, 0, 100);
			baecker.add(i, i * 27, 0, 100);
		}

		// chart
		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, null, null,
				xydataset, false, false, false);

		// plot
		XYPlot plot = (XYPlot) chart.getPlot();

		// colors
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.gray);
		plot.setDomainGridlinePaint(Color.gray);

		// range axis
		ValueAxis rangeAxis = plot.getRangeAxis();
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		// domain axis
		ValueAxis domainAxis = plot.getDomainAxis();

		// legend
		LegendTitle legend = new LegendTitle(plot);
		legend.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		legend.setItemPaint(Color.gray);
		legend.setBackgroundPaint(Color.white);
		legend.setFrame(new BlockBorder(Color.gray));
		legend.setPosition(RectangleEdge.LEFT);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0.02, legend,
				RectangleAnchor.BOTTOM_RIGHT);
		plot.addAnnotation(ta);

		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setChart(chart);
	}

}
