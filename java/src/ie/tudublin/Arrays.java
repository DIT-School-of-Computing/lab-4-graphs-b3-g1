package ie.tudublin;

import processing.core.PApplet;

public class Arrays extends PApplet {
	// Declare variables
	boolean showError = false;
	int errorMessageTimer = 0;
	String errorMessage = "";
	int mode = 1;

	String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	float[] rainfall = { 200, 260, 300, 150, 100, 50, 10, 40, 67, 160, 400, 420 };

	public void settings() {
		size(700, 700);

		String[] m1 = months;
		print(m1[0]);
		for (int i = 0; i < months.length; i++) {
			println("Month: " + months[i] + "\t" + rainfall[i]);
		}
		for (String s : m1) {
			println(s);
		}

		int minIndex = 0;
		for (int i = 0; i < rainfall.length; i++) {
			if (rainfall[i] < rainfall[minIndex]) {
				minIndex = i;
			}
		}

		int maxIndex = 0;
		for (int i = 0; i < rainfall.length; i++) {
			if (rainfall[i] > rainfall[maxIndex]) {
				maxIndex = i;
			}
		}

		println("The month with the minimum rainfall was " + months[minIndex] + " with " + rainfall[minIndex] + "rain");
		println("The month with the max rainfall was " + months[maxIndex] + " with " + rainfall[maxIndex] + "rain");

		float tot = 0;
		for (float f : rainfall) {
			tot += f;
		}

		float avg = tot / (float) rainfall.length;

		// a b-c d-e;
		println(map1(5, 0, 10, 0, 100));
		// 50

		println(map1(25, 20, 30, 200, 300));
		// 250

		println(map1(26, 25, 35, 0, 100));
		// 10

		println(avg);
	}

	public void setup() {
		colorMode(HSB);
		background(0);
		randomize();
	}

	public void draw() {
		background(0);
		textSize(15);
		int ticks = 13;
		float border = 0.1f * width;
		float barWidth = (width - 2 * border) / months.length; // Calculate the width of each bar

		switch (mode) {
			case 1:
				// Start by drawing the axis, then draw the ticks and print the text
				// Then draw the bars
				// Then print the text for the months
				stroke(255);
				line(border, border, border, height - border);
				line(border, height - border, width - border, height - border);

				for (int i = 0; i < ticks; i++) {
					float y = map1(i, 0, ticks - 1, height - border, border); // Mapping y-values from bottom to top
					line(border, y, border - 10, y); // Adjust the length of ticks accordingly
					textAlign(RIGHT, CENTER); // Align text to the right
					fill(255); // Set text color to white
					text(i * 10, border - 15, y); // Adjust the position of text
				}

				// Draw bars
				colorMode(HSB);
				for (int i = 0; i < months.length; i++) {
					float x = border + i * barWidth; // Calculate the x-coordinate for the bar
					float barHeight = map(rainfall[i], 0, 500, height - border, border); // Map rainfall to screen
																							// height
					float hue = map(i, 0, months.length - 1, 0, 255); // Set hue based on position
					fill(hue, 255, 255); // Set bar color using HSB color space
					rect(x, barHeight, barWidth, height - border - barHeight); // Draw the bar
				}

				// Draw month labels
				textAlign(CENTER, CENTER);
				fill(255); // Set text color to white
				for (int i = 0; i < months.length; i++) {
					float x = border + i * barWidth + barWidth / 2; // Center the label within the bar
					text(months[i], x, height - border + 20);
				}
				break;
			case 2:
				// Start by drawing the axis, then draw the ticks and print the text
				stroke(255);
				line(border, border, border, height - border);
				line(border, height - border, width - border, height - border);

				for (int i = 0; i < ticks; i++) {
					float y = map1(i, 0, ticks - 1, height - border, border);
					line(border, y, border - 10, y);
					textAlign(RIGHT, CENTER);
					fill(255);
					text(i * 10, border - 15, y);
				}

				// Draw trend lines
				colorMode(HSB);
				noFill();
				beginShape();
				for (int i = 1; i < months.length; i++) {
					float x1 = border + (i - 1) * barWidth; // Calculate x-coordinate for the start of line segment
					float y1 = map(rainfall[i - 1], 0, 500, height - border, border); // Map previous rainfall to
																						// y-coordinate
					float x2 = border + i * barWidth; // Calculate x-coordinate for the end of line segment
					float y2 = map(rainfall[i], 0, 500, height - border, border); // Map current rainfall to
																					// y-coordinate
					float hue = map(i, 0, months.length - 1, 0, 255); // Set hue based on position
					stroke(hue, 255, 255); // Set line color using HSB color space
					line(x1, y1, x2, y2); // Draw line segment
				}
				endShape();

				// Draw month labels
				textAlign(CENTER, CENTER);
				fill(255); // Set text color to white
				for (int i = 0; i < months.length; i++) {
					float x = border + i * barWidth; // Calculate x-coordinate for label
					text(months[i], x, height - border + 20);
				}
				break;
			case 3:
				// Calculate the sum of all rainfall data
				float sum = 0;
				for (float r : rainfall) {
					sum += r;
				}

				// Draw pie chart segments
				float startAngle = 0;
				for (int i = 0; i < months.length; i++) {
					float angle = map(rainfall[i], 0, sum, 0, TWO_PI); // Calculate angle for the segment
					float hue = map(i, 0, months.length, 0, 255); // Set hue based on position
					fill(hue, 255, 255); // Set fill color using HSB color space
					arc(width / 2, height / 2, 300, 300, startAngle, startAngle + angle); // Draw arc segment
					startAngle += angle; // Update start angle for the next segment

					// Calculate position for label
					float labelX = width / 2 + cos(startAngle - angle / 2) * 175;
					float labelY = height / 2 + sin(startAngle - angle / 2) * 175;
					textAlign(CENTER, CENTER);
					fill(255); // Set text color to white
					text(months[i], labelX, labelY); // Draw label
				}
				break;
			default:
				showError("Invalid input! Please try again.");
				// Display error message
				if (showError) {
					background(0);
					fill(255); // Set text color to white
					textAlign(CENTER, CENTER);
					textSize(20);
					text(errorMessage, width / 2, height / 2);
					// Increment timer
					errorMessageTimer++;
					// After 2 seconds, hide error message
					if (errorMessageTimer > 120) {
						showError = false;
						errorMessageTimer = 0; // Reset timer
					}
				}

		}
	}

	public float map1(float a, float b, float c, float d, float e) {
		float r1 = c - b;
		float r2 = e - d;

		float howFar = a - b;
		return d + (howFar / r1) * r2;
	}

	void randomize() {
		for (int i = 0; i < rainfall.length; i++) {
			rainfall[i] = random(500);
		}
	}

	// Function to show error message
	void showError(String message) {
		errorMessage = message;
		showError = true;
	}

	public void keyPressed() {
		// Check if the 'r' key is pressed
		if (key == 'r' || key == 'R') {
			randomize(); // Randomize the rainfall data
		} else if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
	}
}