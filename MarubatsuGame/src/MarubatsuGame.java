import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MarubatsuGame {

	public static void main(String[] args) throws IOException {
		SoundPlayer win = new SoundPlayer("trumpet.wav");
		SoundPlayer draw = new SoundPlayer("Onmtp-Negative03-3.wav");
		Test1 A = new Test1();
		Test2 B = new Test2();
		Test3 C = new Test3();

		A.board_number();
		char board[] = C.initial_board();
		B.boardDisplay(board);
		String first = A.decideOrder();
		System.out.println("最初は" + first + "からです");
		board = C.game(board, first);
		B.result(board);
		if (board[0] == '〇' && board[1] == '〇' && board[2] == '〇'
				|| board[3] == '〇' && board[4] == '〇' && board[5] == '〇'
				|| board[6] == '〇' && board[7] == '〇' && board[8] == '〇'
				|| board[0] == '〇' && board[4] == '〇' && board[8] == '〇'
				|| board[2] == '〇' && board[4] == '〇' && board[6] == '〇'
				|| board[0] == '〇' && board[3] == '〇' && board[6] == '〇'
				|| board[1] == '〇' && board[4] == '〇' && board[7] == '〇'
				|| board[2] == '〇' && board[5] == '〇' && board[8] == '〇') {
			win.play();

		} else if (board[0] == '×' && board[1] == '×' && board[2] == '×'
				|| board[3] == '×' && board[4] == '×' && board[5] == '×'
				|| board[6] == '×' && board[7] == '×' && board[8] == '×'
				|| board[0] == '×' && board[4] == '×' && board[8] == '×'
				|| board[2] == '×' && board[4] == '×' && board[6] == '×'
				|| board[0] == '×' && board[3] == '×' && board[6] == '×'
				|| board[1] == '×' && board[4] == '×' && board[7] == '×'
				|| board[2] == '×' && board[5] == '×' && board[8] == '×') {
			win.play();
		} else {
			draw.play();

		}

	}
}

class SoundPlayer {

	// フィールド
	private static final int BUFFER_SIZE = 128000; // バッファサイズ

	String soundFileName; // ファイル名

	// コンストラクタ
	public SoundPlayer(String soundFileName) {
		this.soundFileName = soundFileName;
	}

	// メソッド(抽象メソッドの実装)
	public void play() {
		try {
			// バイトのカウンタ
			int nBytesRead = 0;
			// バッファ
			byte[] dat = new byte[BUFFER_SIZE];
			// Fileクラスのインスタンスを生成
			File soundFile = new File(soundFileName);
			// オーディオ入力ストリームを取得
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			// オーディオ形式を取得
			AudioFormat audioFormat = audioInputStream.getFormat();
			// データラインの情報オブジェクトを生成
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			// 指定されたデータライン情報に一致するラインを取得
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			// ラインをオープン
			line.open(audioFormat);
			// ラインでのデータ出力をスタート
			line.start();

			while (nBytesRead != -1) {
				// オーディオストリームからデータ読込み
				nBytesRead = audioInputStream.read(dat, 0, dat.length);
				if (nBytesRead >= 0) {
				}
			}
			// ラインからキューに入っているデータを排出
			line.drain();
			// ラインをクローズ
			line.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
class Test1{
	void board_number() {
		System.out.println("　※このマルバツゲームでは、以下の入力をすると自動的に自分のターンから相手のターンに移ります※");
		System.out.println("・1~9以外の数字を入力した時");
		System.out.println("・一度入力した数字を再び入力した時");
		System.out.println();
		System.out.println("ボードの番号の位置はこの通りです");
		for (int i = 0; i < 9; i++) {
			System.out.print(i + 1 + " ");
			if (i == 2 || i == 5 || i == 8) {
				System.out.println();
			}
		}
		System.out.println();
	}

	String decideOrder() {
		Random r = new Random();
		int a = r.nextInt(2);
		if (a == 0)
			return "マル";
		else
			return "バツ";
	}
}



//勝敗がついたか、引き分けになったかの判定を行う
class Test2 {
	void boardDisplay(char[] board) {
		for (int i = 0; i < 9; i++) {
			System.out.print(board[i]);
			if (i == 2 || i == 5 || i == 8) {
				System.out.println();
			}
		}
	}
// 盤面に〇を書き、盤面の状況を表示
	char[] writeCircle(char[] board) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("空白のマスの中で、〇を書きたい場所の番号を入力してください");
		boardDisplay(board);
		int num = Integer.parseInt(br.readLine());
		if(num>9||num<1) {
			System.out.println("1~9の半角の整数を入力してください");
			System.out.println("相手のターンに移ります");
			return board;
		}
		if (board[num - 1] == '□') {
			board[num - 1] = '〇';
		} else {
			System.out.println("その場所は一度選択したので選べません");
			System.out.println("相手のターンに移ります");
		}
		return board;
	}
//盤面に×を書き、盤面の状況を表示
	char[] writeCross(char[] board) throws IOException {
		System.out.println("空白のマスの中で、×を書きたい場所の番号を入力してください");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boardDisplay(board);
		int num = Integer.parseInt(br.readLine());
		if(num>9||num<1) {
			System.out.println("1~9の半角の整数を入力してください");
			System.out.println("相手のターンに移ります");
			return board;
		}
		if (board[num - 1] == '□') {
			board[num - 1] = '×';
		} else {
			System.out.println("その場所は一度選択したので選べません");
			System.out.println("相手のターンに移ります");
		}
		return board;
	}

	void result(char[] board) {
		System.out.println();
		System.out.println("最終盤面はこうなりました");
		boardDisplay(board);
		if (board[0] == '〇' && board[1] == '〇' && board[2] == '〇'
				|| board[3] == '〇' && board[4] == '〇' && board[5] == '〇'
				|| board[6] == '〇' && board[7] == '〇' && board[8] == '〇'
				|| board[0] == '〇' && board[4] == '〇' && board[8] == '〇'
				|| board[2] == '〇' && board[4] == '〇' && board[6] == '〇'
				|| board[0] == '〇' && board[3] == '〇' && board[6] == '〇'
				|| board[1] == '〇' && board[4] == '〇' && board[7] == '〇'
				|| board[2] == '〇' && board[5] == '〇' && board[8] == '〇') {
			System.out.println();
			System.out.println("〇の勝利！");

		} else if (board[0] == '×' && board[1] == '×' && board[2] == '×'
				|| board[3] == '×' && board[4] == '×' && board[5] == '×'
				|| board[6] == '×' && board[7] == '×' && board[8] == '×'
				|| board[0] == '×' && board[4] == '×' && board[8] == '×'
				|| board[2] == '×' && board[4] == '×' && board[6] == '×'
				|| board[0] == '×' && board[3] == '×' && board[6] == '×'
				|| board[1] == '×' && board[4] == '×' && board[7] == '×'
				|| board[2] == '×' && board[5] == '×' && board[8] == '×') {
			System.out.println();
			System.out.println("×の勝利！");
		} else {
			System.out.println();
			System.out.println("引き分け！");
		}
		System.out.println();

		System.out.println("ゲームを終了します");
	}
}

//盤面に振り分けられた番号を表示させる
class Test3 extends Test2 {

	char[] board = new char[9];

	/*
	 * ゲームを実行させる
	 */
	public char[] game(char[] board, String first) throws IOException {
		int i = 1;
		if (first.equals("マル")) {
			while (i > 0) {
				board = writeCircle(board);
				if (judge(board) == true)
					break;
				writeCross(board);
				if (judge(board) == true)
					break;
			}
		} else {
			while (i > 0) {
				writeCross(board);
				if (judge(board) == true)
					break;
				writeCircle(board);
				if (judge(board) == true)
					break;
			}
		}
		return board;
	}

	char[] initial_board() {
		for (int i = 0; i < 9; i++) {
			board[i] = '□';
		}
		return board;
	}

	boolean judge(char board[]) {
		if (board[0] == '〇' && board[1] == '〇' && board[2] == '〇'
				|| board[3] == '〇' && board[4] == '〇' && board[5] == '〇'
				|| board[6] == '〇' && board[7] == '〇' && board[8] == '〇'
				|| board[0] == '〇' && board[4] == '〇' && board[8] == '〇'
				|| board[2] == '〇' && board[4] == '〇' && board[6] == '〇'
				|| board[0] == '〇' && board[3] == '〇' && board[6] == '〇'
				|| board[1] == '〇' && board[4] == '〇' && board[7] == '〇'
				|| board[2] == '〇' && board[5] == '〇' && board[8] == '〇')
			return true;
		if (board[0] == '×' && board[1] == '×' && board[2] == '×'
				|| board[3] == '×' && board[4] == '×' && board[5] == '×'
				|| board[6] == '×' && board[7] == '×' && board[8] == '×'
				|| board[0] == '×' && board[4] == '×' && board[8] == '×'
				|| board[2] == '×' && board[4] == '×' && board[6] == '×'
				|| board[0] == '×' && board[3] == '×' && board[6] == '×'
				|| board[1] == '×' && board[4] == '×' && board[7] == '×'
				|| board[2] == '×' && board[5] == '×' && board[8] == '×')
			return true;
		else {
			for (int i = 0; i < 9; i++) {
				if (board[i] == '□') {
					return false;
				}
			}
			return true;
		}
	}
}
