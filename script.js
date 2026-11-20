const start=document.querySelector('#start'),stop=document.querySelector('#stop'),statusEl=document.querySelector('#status'),heard=document.querySelector('#heard'),pulse=document.querySelector('#pulse');
const SpeechRecognition=window.SpeechRecognition||window.webkitSpeechRecognition;let recognition=null,active=false;
function say(text){if('speechSynthesis'in window){speechSynthesis.cancel();speechSynthesis.speak(new SpeechSynthesisUtterance(text));}}
function handle(text){const t=text.toLowerCase();heard.textContent='Siz: '+text;
 if(t.includes('to‘xta')||t.includes("to'xta")||t.includes('tinglashni to‘xta')){stopListening();say('Tinglash to‘xtatildi');return;}
 if(t.includes('youtube')){say('YouTube ochilmoqda');window.open('https://www.youtube.com','_blank');}
 else if(t.includes('google')){say('Google ochilmoqda');window.open('https://www.google.com','_blank');}
 else if(t.includes('gmail')){say('Gmail ochilmoqda');window.open('https://mail.google.com','_blank');}
 else if(t.includes('soat')||t.includes('vaqt')){const d=new Date();say('Hozir soat '+d.getHours()+' dan '+d.getMinutes()+' minut o‘tdi');}
 else if(t.includes('sana')){say('Bugun '+new Date().toLocaleDateString('uz-UZ',{dateStyle:'long'}));}
 else if(t.includes('qidir')){const q=text.replace(/google(da)?/ig,'').replace(/qidir(ib ber)?/ig,'').trim();say('Qidiruv ochilmoqda');window.open('https://www.google.com/search?q='+encodeURIComponent(q),'_blank');}
 else if(t.includes('salom'))say('Assalomu alaykum. Qanday yordam beray?');
 else if(t.includes('yordam'))say('YouTube och, Google och, soat nechi, bugungi sana yoki to‘xta deb ayting');
 else say('Bu buyruqni hali tushunmadim');
}
function startListening(){if(!SpeechRecognition){heard.textContent='Bu brauzer ovoz tanishni qo‘llamaydi. Chrome brauzeridan foydalaning.';return;}if(active)return;recognition=new SpeechRecognition();recognition.lang='uz-UZ';recognition.continuous=true;recognition.interimResults=false;recognition.onresult=e=>{const r=e.results[e.results.length-1];if(r.isFinal)handle(r[0].transcript);};recognition.onerror=e=>{statusEl.textContent='Xatolik: '+e.error;};recognition.onend=()=>{if(active){try{recognition.start();}catch(_){}}};active=true;recognition.start();start.disabled=true;stop.disabled=false;statusEl.textContent='Tinglayapti';pulse.classList.add('active');heard.textContent='Eshitish uchun gapiring…';}
function stopListening(){active=false;if(recognition)recognition.stop();start.disabled=false;stop.disabled=true;statusEl.textContent='To‘xtatildi';pulse.classList.remove('active');}
start.addEventListener('click',startListening);stop.addEventListener('click',stopListening);
