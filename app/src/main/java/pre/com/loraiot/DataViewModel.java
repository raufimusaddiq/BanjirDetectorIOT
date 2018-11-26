package pre.com.loraiot;

public class DataViewModel {
    int Image;
    String Status;
    String DistanceString;

    DataViewModel(){

    }

    DataViewModel(int image, String status, String distanceString){
        this.Image = image;
        this.Status = status;
        this.DistanceString = distanceString;
    }

    public int getImage(int distance) {
        int statusCalculate = this.calculate(distance);
        switch (statusCalculate){
            case 0:
                Image = R.drawable.ic_shield;
                break;
            case 1:
                Image = R.drawable.ic_running;
                break;
            case 2:
                Image = R.drawable.ic_warning;
                break;
            case 3:
                Image = R.drawable.ic_complain;
                break;
            default:
                Image = R.drawable.ic_shield;
                break;
        }
        return Image;
    }

    public void setImage(int image) {
        this.Image = image;
    }

    public String getStatus(int distance) {
        int statusCalculate = this.calculate(distance);
        switch (statusCalculate){
            case 0:
                Status = "Aman";
                break;
            case 1:
                Status = "Siaga 1";
                break;
            case 2:
                Status = "Siaga 2";
                break;
            case 3:
                Status = "Siaga 3";
                break;
            default:
                Status = "Aman";
                break;
        }
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getDistanceString(int distance) {
        distance = 0;
        DistanceString = "Jarak Permukaan Air :" + String.valueOf(distance);
        return DistanceString;
    }

    public void setDistanceString(String distanceString) {
        this.DistanceString = distanceString;
    }

    private int calculate(int distance){
        int status = 0;
        if (distance >= 0 && distance <= 99) {
            status = 1;
        } else if (distance >= 100 && distance < 149) {
            status = 2;
        } else if (distance >= 150 && distance < 199) {
            status = 3;
        } else {
            status = 0;
        }
        return status;
    }
}
