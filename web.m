x = videoinput('winvideo',1);
for i = 1:10
    img = getsnapshot(x);
    fname = [ 'image',num2str(i)];
    imwrite(img,fname,'jpg');
    pause(1);
end

    