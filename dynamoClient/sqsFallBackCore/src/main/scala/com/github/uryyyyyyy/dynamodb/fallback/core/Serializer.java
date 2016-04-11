package com.github.uryyyyyyy.dynamodb.fallback.core;

import java.io.*;

//from Spring framework
public class Serializer<T extends Serializable> {

	private static final char[] HEX = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

	static byte[] decode(CharSequence s) {
		int nChars = s.length();

		if (nChars % 2 != 0) {
			throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
		}

		byte[] result = new byte[nChars / 2];

		for (int i = 0; i < nChars; i += 2) {
			int msb = Character.digit(s.charAt(i), 16);
			int lsb = Character.digit(s.charAt(i + 1), 16);

			if (msb < 0 || lsb < 0) {
				throw new IllegalArgumentException("Non-hex character in input: " + s);
			}
			result[i / 2] = (byte) ((msb << 4) | lsb);
		}
		return result;
	}

	static char[] encode(byte[] bytes) {
		final int nBytes = bytes.length;
		char[] result = new char[2 * nBytes];

		int j = 0;
		for (int i = 0; i < nBytes; i++) {
			// Char for top 4 bits
			result[j++] = HEX[(0xF0 & bytes[i]) >>> 4];
			// Bottom 4
			result[j++] = HEX[(0x0F & bytes[i])];
		}

		return result;
	}

	public String serialize(T serializable) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
			out.writeObject(serializable);
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception", e);
		}
		return new String(encode(baos.toByteArray()));
	}

	@SuppressWarnings("unchecked")
	public T deserialize(String serialized) {
		byte[] decoded = decode(serialized);
		try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(decoded))) {
			return (T) in.readObject();
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception", e);
		}
	}
}