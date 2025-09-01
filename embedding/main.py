from fastapi import FastAPI
from pydantic import BaseModel
from sentence_transformers import SentenceTransformer
app = FastAPI()
model = SentenceTransformer("intfloat/multilingual-e5-base")

class TextRequest(BaseModel):
    text: str
@app.post("/embed")
def embed(request: TextRequest):
    vector = model.encode([request.text])[0].tolist()
    return {"embedding": vector}