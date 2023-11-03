import { useState } from "react"

let recognition: SpeechRecognition | null = null;

if ("webkitSpeechRecognition" in window) {
  recognition = new window.webkitSpeechRecognition()
  recognition.continuous = true
  recognition.lang = "ko-KR"
}

const SoundToText = (setText: React.Dispatch<React.SetStateAction<string>>) => {
  const [accentText, setAccentText] = useState<string>("말하세요");
  const [isListening, setIsListening] = useState<boolean>(false)

  // STT 시작
  const startListening = () => {
    if (recognition && !isListening) {
      setText("");
      setAccentText("")
      recognition.start() 
      setIsListening(true)
      console.log("STT시작",true)

      recognition.onresult = (event: SpeechRecognitionEvent) => {
        let fullTranscript = "";
        for (let i = 0; i < event.results.length; i++) {
          fullTranscript += event.results[i][0].transcript;}
          setText(fullTranscript);
          setAccentText(fullTranscript)
      }
    }
  }

  // STT 종료
  const stopListening = () => {
    if  (recognition && isListening) {
      recognition.stop()
      setIsListening(false)
      console.log("STT종료",false)
    } 
  }

  return {
    startListening,
    stopListening,
    accentText,
    hasRecognitionSupport: !! recognition
  }
}

export default SoundToText
