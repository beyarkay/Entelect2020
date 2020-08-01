public class NiceBattery {
	public Shape shape;
	public int rotation;
	public int row, col;

	public NiceBattery(Shape shape, int rotation, int row, int col) {
		this.shape = shape;
		this.rotation = rotation;
		this.row = row;
		this.col = col;
	}

	public Battery toShitBattery() {
		Battery shitBattery = new Battery(shape);
		shitBattery.offset[0] = row;
		shitBattery.offset[1] = col;
		shitBattery.rotationId = rotation;
		return shitBattery;
	}
}
