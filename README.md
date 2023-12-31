# Data Compression — Assignment 5: 2D Prediction Coding

Java implementation of **2D prediction-based image compression**.

## How it works
- Predicts each pixel value based on its neighboring pixels (left, above, or a combination)
- Computes the **prediction error** (residual) = actual value − predicted value
- Encodes only the residuals, which are typically small and clustered around zero — making them highly compressible
- Decompression reverses the process: reconstructs each pixel from the prediction + stored residual

2D prediction exploits spatial correlation in images; smooth regions produce near-zero residuals that compress very efficiently.

## Run
```bash
javac src/Main.java
java Main
```
