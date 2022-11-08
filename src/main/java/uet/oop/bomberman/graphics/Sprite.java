package uet.oop.bomberman.graphics;

import javafx.scene.image.*;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game).
 */
public class Sprite {
	//constant size của ảnh gốc
	public static final int DEFAULT_SIZE = 24;
	//size entity trong game
	public static final int SCALED_SIZE = DEFAULT_SIZE * 2;
	//màu nền
	private static final int TRANSPARENT_COLOR = 0xffff00ff;
	//size khi lấy sprite từ ảnh
	public final int SIZE;
	//tọa độ gốc
	private int _x, _y;
	public int[] _pixels;
	//chiều rộng gốc
	protected int _realWidth;
	//chiều cao gốc
	protected int _realHeight;
	private SpriteSheet _sheet;

	private Image image;
	/*
	|--------------------------------------------------------------------------
	| Board sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite grass = new Sprite(DEFAULT_SIZE, 5, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite brick = new Sprite(DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite wall = new Sprite(DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite portal = new Sprite(DEFAULT_SIZE, 5, 2, SpriteSheet.tiles, 24, 24);
	
	/*
	|--------------------------------------------------------------------------
	| Bomber Sprites
	| Player 1
	|--------------------------------------------------------------------------
	 */
	public static Sprite player1_up = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_down = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_left = new Sprite(DEFAULT_SIZE, 3, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_right = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.tiles, 24, 24);

	public static Sprite player1_up_1 = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_up_2 = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.tiles, 24, 24);

	public static Sprite player1_down_1 = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_down_2 = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.tiles, 24, 24);

	public static Sprite player1_left_1 = new Sprite(DEFAULT_SIZE, 3, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_left_2 = new Sprite(DEFAULT_SIZE, 3, 2, SpriteSheet.tiles, 24,24);

	public static Sprite player1_right_1 = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_right_2 = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.tiles, 24, 24);

	public static Sprite player1_dead1 = new Sprite(DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_dead2 = new Sprite(DEFAULT_SIZE, 4, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite player1_dead3 = new Sprite(DEFAULT_SIZE, 4, 2, SpriteSheet.tiles, 24, 24);

	/**
	 * Player 2
	 */

	public static Sprite player2_up = new Sprite(DEFAULT_SIZE, 0, 11, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_down = new Sprite(DEFAULT_SIZE, 2, 11, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_left = new Sprite(DEFAULT_SIZE, 3, 11, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_right = new Sprite(DEFAULT_SIZE, 1, 11, SpriteSheet.tiles, 24, 24);

	public static Sprite player2_up_1 = new Sprite(DEFAULT_SIZE, 0, 12, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_up_2 = new Sprite(DEFAULT_SIZE, 0, 13, SpriteSheet.tiles, 24, 24);

	public static Sprite player2_down_1 = new Sprite(DEFAULT_SIZE, 2, 12, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_down_2 = new Sprite(DEFAULT_SIZE, 2, 13, SpriteSheet.tiles, 24, 24);

	public static Sprite player2_left_1 = new Sprite(DEFAULT_SIZE, 3, 12, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_left_2 = new Sprite(DEFAULT_SIZE, 3, 13, SpriteSheet.tiles, 24,24);

	public static Sprite player2_right_1 = new Sprite(DEFAULT_SIZE, 1, 12, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_right_2 = new Sprite(DEFAULT_SIZE, 1, 13, SpriteSheet.tiles, 24, 24);

	public static Sprite player2_dead1 = new Sprite(DEFAULT_SIZE, 4, 11, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_dead2 = new Sprite(DEFAULT_SIZE, 4, 12, SpriteSheet.tiles, 24, 24);
	public static Sprite player2_dead3 = new Sprite(DEFAULT_SIZE, 4, 13, SpriteSheet.tiles, 24, 24);
	/*
	|--------------------------------------------------------------------------
	| Character
	|--------------------------------------------------------------------------
	 */
	//BALLOM
	public static Sprite spartan_left1 = new Sprite(DEFAULT_SIZE, 3, 3, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_left2 = new Sprite(DEFAULT_SIZE, 3, 4, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_left3 = new Sprite(DEFAULT_SIZE, 3, 5, SpriteSheet.tiles, 24, 24);

	public static Sprite spartan_right1 = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_right2 = new Sprite(DEFAULT_SIZE, 1, 4, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_right3 = new Sprite(DEFAULT_SIZE, 1, 5, SpriteSheet.tiles, 24, 24);

	public static Sprite spartan_up1 = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_up2 = new Sprite(DEFAULT_SIZE, 0, 4, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_up3 = new Sprite(DEFAULT_SIZE, 0, 5, SpriteSheet.tiles, 24, 24);

	public static Sprite spartan_down1 = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_down2 = new Sprite(DEFAULT_SIZE, 2, 4, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_down3 = new Sprite(DEFAULT_SIZE, 2, 5, SpriteSheet.tiles, 24, 24);
	public static Sprite spartan_dead = new Sprite(DEFAULT_SIZE, 4, 3, SpriteSheet.tiles, 24, 24);

	//ONEAL
	public static Sprite mushroom_left1 = new Sprite(DEFAULT_SIZE, 3, 6, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_left2 = new Sprite(DEFAULT_SIZE, 3, 7, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_left3 = new Sprite(DEFAULT_SIZE, 3, 8, SpriteSheet.tiles, 24, 24);

	public static Sprite mushroom_right1 = new Sprite(DEFAULT_SIZE, 1, 6, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_right2 = new Sprite(DEFAULT_SIZE, 1, 7, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_right3 = new Sprite(DEFAULT_SIZE, 1, 8, SpriteSheet.tiles, 24, 24);

	public static Sprite mushroom_up1 = new Sprite(DEFAULT_SIZE, 0, 6, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_up2 = new Sprite(DEFAULT_SIZE, 0, 7, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_up3 = new Sprite(DEFAULT_SIZE, 0, 8, SpriteSheet.tiles, 24, 24);

	public static Sprite mushroom_down1 = new Sprite(DEFAULT_SIZE, 2, 6, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_down2 = new Sprite(DEFAULT_SIZE, 2, 7, SpriteSheet.tiles, 24, 24);
	public static Sprite mushroom_down3 = new Sprite(DEFAULT_SIZE, 2, 8, SpriteSheet.tiles, 24, 24);

	public static Sprite mushroom_dead = new Sprite(DEFAULT_SIZE, 4, 6, SpriteSheet.tiles, 24, 24);

	public static Sprite ufo_left1 = new Sprite(DEFAULT_SIZE, 3, 9, SpriteSheet.tiles, 24, 24);
	public static Sprite ufo_left2 = new Sprite(DEFAULT_SIZE, 3, 10, SpriteSheet.tiles, 24,24);
	public static Sprite ufo_left3 = new Sprite(DEFAULT_SIZE, 3, 9, SpriteSheet.tiles, 24, 24);
	
	public static Sprite ufo_right1 = new Sprite(DEFAULT_SIZE, 1, 9, SpriteSheet.tiles, 24, 24);
	public static Sprite ufo_right2 = new Sprite(DEFAULT_SIZE, 1, 10, SpriteSheet.tiles, 24, 24);
	public static Sprite ufo_right3 = new Sprite(DEFAULT_SIZE, 1, 9, SpriteSheet.tiles, 24, 24);
	
	public static Sprite ufo_up1 = new Sprite(DEFAULT_SIZE, 0, 9, SpriteSheet.tiles, 24, 24);
	public static Sprite ufo_up2 = new Sprite(DEFAULT_SIZE, 0, 10, SpriteSheet.tiles, 24, 24);
	public static Sprite ufo_up3 = new Sprite(DEFAULT_SIZE, 0, 9, SpriteSheet.tiles, 24, 24);
	
	public static Sprite ufo_down1 = new Sprite(DEFAULT_SIZE, 2, 9, SpriteSheet.tiles, 24, 24);
	public static Sprite ufo_down2 = new Sprite(DEFAULT_SIZE, 2, 10, SpriteSheet.tiles, 24, 24);
	public static Sprite ufo_down3 = new Sprite(DEFAULT_SIZE, 2, 9, SpriteSheet.tiles, 24, 24);
	
	public static Sprite ufo_dead = new Sprite(DEFAULT_SIZE, 4, 9, SpriteSheet.tiles, 24, 24);

	/*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb = new Sprite(DEFAULT_SIZE, 7, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite bomb_1 = new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite bomb_2 = new Sprite(DEFAULT_SIZE, 7, 2, SpriteSheet.tiles, 24, 24);

	/*
	|--------------------------------------------------------------------------
	| FlameSegment Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb_exploded = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 24, 24);
	public static Sprite bomb_exploded1 = new Sprite(DEFAULT_SIZE, 6, 5, SpriteSheet.tiles, 24, 24);
	public static Sprite bomb_exploded2 = new Sprite(DEFAULT_SIZE, 6, 6, SpriteSheet.tiles, 24, 24);

	public static Sprite explosion_vertical = new Sprite(DEFAULT_SIZE, 7, 5, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_vertical1 = new Sprite(DEFAULT_SIZE, 8, 5, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_vertical2 = new Sprite(DEFAULT_SIZE, 9, 5, SpriteSheet.tiles, 24, 24);

	public static Sprite explosion_horizontal = new Sprite(DEFAULT_SIZE, 7, 7, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_horizontal1 = new Sprite(DEFAULT_SIZE, 7, 8, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_horizontal2 = new Sprite(DEFAULT_SIZE, 7, 9, SpriteSheet.tiles, 24, 24);

	public static Sprite explosion_horizontal_left_last = new Sprite(DEFAULT_SIZE, 6, 7, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_horizontal_left_last1 = new Sprite(DEFAULT_SIZE, 6, 8, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_horizontal_left_last2 = new Sprite(DEFAULT_SIZE, 6, 9, SpriteSheet.tiles, 24, 24);

	public static Sprite explosion_horizontal_right_last = new Sprite(DEFAULT_SIZE, 8, 7, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_horizontal_right_last1 = new Sprite(DEFAULT_SIZE, 8, 8, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_horizontal_right_last2 = new Sprite(DEFAULT_SIZE, 8, 9, SpriteSheet.tiles, 24, 24);

	public static Sprite explosion_vertical_top_last = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_vertical_top_last1 = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_vertical_top_last2 = new Sprite(DEFAULT_SIZE, 9, 4, SpriteSheet.tiles, 24, 24);

	public static Sprite explosion_vertical_down_last = new Sprite(DEFAULT_SIZE, 7, 6, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_vertical_down_last1 = new Sprite(DEFAULT_SIZE, 8, 6, SpriteSheet.tiles, 24, 24);
	public static Sprite explosion_vertical_down_last2 = new Sprite(DEFAULT_SIZE, 9, 6, SpriteSheet.tiles, 24, 24);

	public static Sprite powerup_life = new Sprite(DEFAULT_SIZE, 9, 0, SpriteSheet.tiles, 24, 24);
	/*
	|--------------------------------------------------------------------------
	| Brick FlameSegment
	|--------------------------------------------------------------------------
	 */
	public static Sprite brick_exploded = new Sprite(DEFAULT_SIZE, 6, 1, SpriteSheet.tiles, 24, 24);
	public static Sprite brick_exploded1 = new Sprite(DEFAULT_SIZE, 6, 2, SpriteSheet.tiles, 24, 24);
	public static Sprite brick_exploded2 = new Sprite(DEFAULT_SIZE, 6, 3, SpriteSheet.tiles, 24, 24);

	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
	public static Sprite powerup_bombs = new Sprite(DEFAULT_SIZE, 7, 0, SpriteSheet.tiles, 24, 24);
	public static Sprite powerup_flames = new Sprite(DEFAULT_SIZE, 9, 2, SpriteSheet.tiles, 24, 24);
	public static Sprite powerup_speed = new Sprite(DEFAULT_SIZE, 9, 1, SpriteSheet.tiles, 24, 24);


	public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = x * SIZE;
		_y = y * SIZE;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
		image = this.getFxImage();
	}

	public Sprite(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				_pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
			}
		}
	}

	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;

		if(calc < diff) {
			return normal;
		}

		if(calc < diff * 2) {
			return x1;
		}

		return x2;
	}

	public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2;
	}

	public int getSize() {
		return SIZE;
	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	public Image getFxImage() {
		WritableImage wr = new WritableImage(SIZE, SIZE);
		PixelWriter pw = wr.getPixelWriter();
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
					pw.setArgb(x, y, 0);
				}
				else {
					pw.setArgb(x, y, _pixels[x + y * SIZE]);
				}
			}
		}
		Image input = new ImageView(wr).getImage();
		return resample(input, SCALED_SIZE / DEFAULT_SIZE);
	}

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(
				W * S,
				H * S
		);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}

		return output;
	}

	public Image getImage() {
		return image;
	}
}