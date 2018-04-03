package com.google.maps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.maps.model.Size;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;

public class StaticMapsApiTest {

  BufferedImage bufferedImage;

  @Before
  public void setUp() throws IOException {
    bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
  }

  @Test
  public void testGetSydneyStaticMap() throws Exception {
    try (LocalTestServerContext sc = new LocalTestServerContext(bufferedImage)) {

      StaticMapsRequest req = StaticMapsApi.newRequest(sc.context, new Size(300, 300));
      req.center("Google Sydney");
      req.zoom(16);
      ImageResult result = req.await();
      ByteArrayInputStream input = new ByteArrayInputStream(result.imageData);
      BufferedImage img = ImageIO.read(input);

      assertNotNull(img);
      assertEquals(300, img.getWidth());
      assertEquals(300, img.getHeight());
    }
  }
}
