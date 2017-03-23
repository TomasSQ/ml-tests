const Vision = require('@google-cloud/vision');

const projectId = 'ml-tests-162400';

const visionClient = Vision({
  projectId: projectId
});


const fileNames = [...Array(9)].map((v,i)=>`./src/main/resources/0${i+1}.jpg`).concat('./src/main/resources/10.jpg');
const myFaceIndex = [0, 1, 1, 1, 1, 0, 0, 0, 0, 1];

detectFace = (fileNames, indexs) => {
	setTimeout(() => {
		console.info(`Trying ${fileNames[0]}. Remaning ${fileNames.length}`);
		if (fileNames.length === 0) {
			return;
		}

		visionClient.detectFaces(fileNames[0]).then((results) => {
			const faces = results[0];

			console.log(fileNames[0], JSON.stringify(faces[indexs[0]].bounds.face));
			detectFace(fileNames.slice(1), indexs.slice(1));
		}).catch(error => console.info(error));
	}, 100 * 1000);
}

detectFace(fileNames, myFaceIndex);
