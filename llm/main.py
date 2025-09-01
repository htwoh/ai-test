from fastapi import FastAPI
from pydantic import BaseModel
from gpt4all import GPT4All

app = FastAPI()
model = GPT4All("gpt4all-j")

class PromptRequest(BaseModel):
    prompt: str

@app.post("/generate")
def generate(request: PromptRequest):
    output = model.generate(request.prompt)
    return {"text": output}