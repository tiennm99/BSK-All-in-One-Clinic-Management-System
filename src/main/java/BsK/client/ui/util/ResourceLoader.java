package BsK.client.ui.util;

import javax.swing.ImageIcon;
import java.io.InputStream;
import java.net.URL;

/**
 * Utility class for loading resources from classpath.
 * This ensures resources work both in IDE and in packaged JAR files.
 */
public class ResourceLoader {
    
    /**
     * Load an ImageIcon from the resources directory.
     * @param resourcePath Path relative to resources directory (e.g., "/assets/icon/logo.jpg")
     * @return ImageIcon or null if not found
     */
    public static ImageIcon loadIcon(String resourcePath) {
        try {
            URL resourceUrl = ResourceLoader.class.getResource(resourcePath);
            if (resourceUrl != null) {
                return new ImageIcon(resourceUrl);
            }
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + resourcePath + " - " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Load an InputStream for a resource.
     * @param resourcePath Path relative to resources directory (e.g., "/print_forms/template.jrxml")
     * @return InputStream or null if not found
     */
    public static InputStream loadResourceStream(String resourcePath) {
        return ResourceLoader.class.getResourceAsStream(resourcePath);
    }
    
    /**
     * Get the URL of a resource.
     * @param resourcePath Path relative to resources directory
     * @return URL or null if not found
     */
    public static URL getResourceUrl(String resourcePath) {
        return ResourceLoader.class.getResource(resourcePath);
    }
    
    /**
     * Convenience method to load icons from the assets/icon directory.
     * @param iconName Icon filename without path (e.g., "logo.jpg")
     * @return ImageIcon or null if not found
     */
    public static ImageIcon loadAssetIcon(String iconName) {
        return loadIcon("/assets/icon/" + iconName);
    }
    
    /**
     * Convenience method to load images from the assets/img directory.
     * @param imageName Image filename without path (e.g., "background.jpg")
     * @return ImageIcon or null if not found
     */
    public static ImageIcon loadAssetImage(String imageName) {
        return loadIcon("/assets/img/" + imageName);
    }
    
    /**
     * Get the absolute path to a resource as a string.
     * Useful for file operations that require string paths.
     * @param resourcePath Path relative to resources directory
     * @return Absolute path string or null if not found
     */
    public static String getResourcePath(String resourcePath) {
        URL url = getResourceUrl(resourcePath);
        return url != null ? url.getPath() : null;
    }
}