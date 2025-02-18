package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;


/***
 * Since we want to share the same unique clipboard between multiple edition windows, we implement of "Singleton pattern" which ensures Clipboard has only one instance and provides a global point of access to that instance.
 */
public class Clipboard {
	
	// singleton instance, it is static to ensure it is shared between multiple objects of this class
	private static Clipboard clipBoard;
	// list of clip that contains the clipboard
	private List<Clip> lclipBoard;
	private List<ClipboardListener> listeners;
	
	/***
	 * Private constructor to ensure that an instance of this class can only be created by the class itself
	 */
	private Clipboard() {
		lclipBoard = new ArrayList<>();
		listeners = new ArrayList<>();
	}
	
	/***
	 * Copy a list of selected clip(s) on clipboard
	 * @param clips
	 */
	public void copyToClipboard(List<Clip> clips) {
		lclipBoard.clear();
		for (Clip c : clips) {
			lclipBoard.add(c.copy());
		}
		// notify to gray out the paste option of all edition windows
		this.notifyListeners();
	}
	
	/**
	 * Access to the list of selected clip(s) on clipboard
	 * @return
	 */
	public List<Clip> copyFromClipboard() {
		List<Clip> lclips = new ArrayList<>();
		for (Clip c : lclipBoard) {
			lclips.add(c.copy());
		}
		return lclips;
	}
	
	/***
	 * Delete all the clip(s) on clipboard
	 */
	// TODO clear the clipboard is not implemented 
	public void clear() {
		lclipBoard.clear();
		this.notifyListeners();
	}
	
	/***
	 * Check if the clipboard is empty
	 * @return
	 */
	public boolean isEmpty() {
		if (lclipBoard.size() > 0)
			return false;
		else
			return true;
	}

	/***
	 * Provides a global access point to the unique instance of the Clipboard class.
	 * Multiple objects reference the same unique instance of ClipBoard since we want to share the same unique clipboard between multiple edition windows.
	 * If the method is not static, we need an instance of Clipboard to call this method, which contradicts the Singleton idea where the method must be callable without needing an existing instance.
	 * @return
	 */
	public static Clipboard getInstance() {
		// create a single instance of Clipboard if it does not exist
		if (clipBoard == null) {
			clipBoard = new Clipboard();
		}
		return clipBoard;
	}
	
	/***
	 * Subscribe to notifications
	 * @param listener
	 */
	public void addListener(ClipboardListener listener) {
		listeners.add(listener);
	}
	
	/***
	 * Unsubscribe to notifications
	 * @param listener
	 */
	public void removeListener(ClipboardListener listener) {
		listeners.remove(listener);
	}
	
	/***
	 * 	Notify to gray out the paste option of all edition windows
	 */
	private void notifyListeners() {
		for (ClipboardListener listener : this.listeners) {
			listener.clipboardChanged();
		}
	}
}
