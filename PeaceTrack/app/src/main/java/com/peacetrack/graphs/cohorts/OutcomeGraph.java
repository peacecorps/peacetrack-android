package com.peacetrack.graphs.cohorts;

import android.content.Context;
import android.graphics.Color;

import com.peacetrack.models.measurements.Measurement;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OutcomeGraph {
    public GraphicalView getGraphicalView(Context context, ArrayList<Measurement> measurements) {
        ArrayList<Measurement> outcome1, outcome2, outcome3, outcome4, outcome5;

        outcome1 = new ArrayList<>();
        outcome2 = new ArrayList<>();
        outcome3 = new ArrayList<>();
        outcome4 = new ArrayList<>();
        outcome5 = new ArrayList<>();

        for(int i=0; i<measurements.size(); i++) {
            int outcome = measurements.get(i).getOutcome();
            switch (outcome) {
                case 0:
                    outcome1.add(measurements.get(i));
                    break;
                case 1:
                    outcome2.add(measurements.get(i));
                    break;
                case 2:
                    outcome3.add(measurements.get(i));
                    break;
                case 3:
                    outcome4.add(measurements.get(i));
                    break;
                case 4:
                    outcome5.add(measurements.get(i));
                    break;
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");

        TimeSeries series1 = new TimeSeries("Outcome 1");
        for(int i=0; i<outcome1.size(); i++) {
            try {
                series1.add(formatter.parse(outcome1.get(i).getDate()), Double.parseDouble(outcome1.get(i).getOutcomeData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        TimeSeries series2 = new TimeSeries("Outcome 2");
        for(int i=0; i<outcome2.size(); i++) {
            try {
                series2.add(formatter.parse(outcome2.get(i).getDate()), Double.parseDouble(outcome2.get(i).getOutcomeData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        TimeSeries series3 = new TimeSeries("Outcome 3");
        for(int i=0; i<outcome3.size(); i++) {
            try {
                series3.add(formatter.parse(outcome3.get(i).getDate()), Double.parseDouble(outcome3.get(i).getOutcomeData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        TimeSeries series4 = new TimeSeries("Outcome 4");
        for(int i=0; i<outcome4.size(); i++) {
            try {
                series4.add(formatter.parse(outcome4.get(i).getDate()), Double.parseDouble(outcome4.get(i).getOutcomeData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        TimeSeries series5 = new TimeSeries("Outcome 5");
        for(int i=0; i<outcome5.size(); i++) {
            try {
                series5.add(formatter.parse(outcome5.get(i).getDate()), Double.parseDouble(outcome5.get(i).getOutcomeData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);

        XYMultipleSeriesRenderer aRenderer = new XYMultipleSeriesRenderer();;
        aRenderer.setChartTitleTextSize(30);
        aRenderer.setAxisTitleTextSize(30);
        aRenderer.setLegendTextSize(30);
        aRenderer.setChartTitle("Cohort Outcome Progress");
        aRenderer.setXTitle("Date");
        aRenderer.setYTitle("Outcome Value");
        aRenderer.setApplyBackgroundColor(true);
        aRenderer.setBackgroundColor(Color.DKGRAY);
        aRenderer.setMarginsColor(Color.DKGRAY);
        
        XYSeriesRenderer renderer1 = new XYSeriesRenderer();
        renderer1.setLineWidth(5);
        renderer1.setColor(Color.BLUE);
        renderer1.setPointStyle(PointStyle.CIRCLE);
        renderer1.setFillPoints(true);
        aRenderer.addSeriesRenderer(renderer1);

        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setLineWidth(5);
        renderer2.setColor(Color.GREEN);
        renderer2.setPointStyle(PointStyle.SQUARE);
        renderer2.setFillPoints(true);
        aRenderer.addSeriesRenderer(renderer2);

        XYSeriesRenderer renderer3 = new XYSeriesRenderer();
        renderer3.setLineWidth(5);
        renderer3.setColor(Color.CYAN);
        renderer3.setPointStyle(PointStyle.SQUARE);
        renderer3.setFillPoints(true);
        aRenderer.addSeriesRenderer(renderer3);

        XYSeriesRenderer renderer4 = new XYSeriesRenderer();
        renderer4.setLineWidth(5);
        renderer4.setColor(Color.RED);
        renderer4.setPointStyle(PointStyle.SQUARE);
        renderer4.setFillPoints(true);
        aRenderer.addSeriesRenderer(renderer4);

        XYSeriesRenderer renderer5 = new XYSeriesRenderer();
        renderer5.setLineWidth(5);
        renderer5.setColor(Color.MAGENTA);
        renderer5.setPointStyle(PointStyle.SQUARE);
        renderer5.setFillPoints(true);
        aRenderer.addSeriesRenderer(renderer5);

        aRenderer.setFitLegend(true);
        GraphicalView gView = ChartFactory.getLineChartView(context, dataset, aRenderer);

        return gView;
    }
}
