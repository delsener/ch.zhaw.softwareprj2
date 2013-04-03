package ch.zhaw.dynsys.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

import ch.zhaw.dynsys.simulation.SimulationListener;

public class GraphPanel extends ChartPanel implements SimulationListener {
	private static final long serialVersionUID = 1L;
	
	private double iteration = 0;
	private YIntervalSeriesCollection datasets = new YIntervalSeriesCollection();
	private JFreeChart chart;
	private XYPlot plot;

	public GraphPanel() {
		super(null);
		setPreferredSize(new Dimension(800, 600));

		// chart
		chart = ChartFactory.createTimeSeriesChart(null, null, null,
				datasets, false, false, false);

		// plot
		plot = (XYPlot) chart.getPlot();

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

	@Override
	public void evolved(final double[] values) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {		    	
		    	for (int i = 0; i < values.length; i++) {
		    		if (i >= datasets.getSeriesCount()) {
		    			datasets.addSeries(new YIntervalSeries(i));
		    		}
		    		YIntervalSeries dataset = datasets.getSeries(i);
		    		dataset.add(iteration, values[i], 0, 0);
		    	}
		    	
		    	// increase iteration
		    	iteration++;
		    	
		    	// update chart
		    	revalidate();
		    	repaint();
		    }
		  });
	}

	@Override
	public void started() {}
	@Override
	public void stoped() {}

}
